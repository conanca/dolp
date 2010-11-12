package gs.dolp.common.jstree.service;

import gs.dolp.common.jstree.domain.JsTreeNode;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.service.IdEntityService;

public class JsTreeNodeService extends IdEntityService<JsTreeNode> {
	private static final Log log = Logs.getLog(JsTreeNodeService.class);

	public JsTreeNodeService(Dao dao) {
		super(dao);
	}

	public JsTreeNode getNode(int id) {
		return fetch(id);
	}

	public List<JsTreeNode> getChildren(int id, boolean recursive) {
		Condition cnd;
		if (recursive) {
			JsTreeNode node = getNode(id);
			cnd = Cnd.where("LEFT", ">=", node.getLeft()).and("RIGHT", "<=", node.getRight()).asc("LEFT");
		} else {
			cnd = Cnd.where("PARENTID", "=", id).asc("POSITION");
		}
		List<JsTreeNode> children = query(cnd, null);
		log.debug(children);
		return children;
	}

	public List<JsTreeNode> getPath(int id) {
		JsTreeNode node = getNode(id);
		if (node == null) {
			return null;
		}
		Condition cnd = Cnd.where("LEFT", "<=", node.getLeft()).and("RIGHT", ">=", node.getRight());
		List<JsTreeNode> path = query(cnd, null);
		log.debug(path);
		return path;
	}

	public boolean move(int id) {
		if (id == 1) {
			return false;
		}
		JsTreeNode data = getNode(id);
		int lft = data.getLeft();
		int rgt = data.getRight();
		int dif = rgt - lft;
		// deleting node and its children
		Condition cnd = Cnd.where("LEFT", ">=", lft).and("RIGHT", "<=", rgt);
		clear(cnd);
		// shift left indexes of nodes right of the node
		Sql sql = Sqls.create("UPDATE SYSTEM_JSTREENODE SET LEFT = LEFT - @dif WHERE LEFT > @rgt");
		sql.params().set("dif", dif).set("rgt", rgt);
		dao().execute(sql);
		// shift right indexes of nodes right of the node and the node's parents
		sql = Sqls.create("UPDATE SYSTEM_JSTREENODE SET RIGHT = RIGHT - @dif WHERE RIGHT > @lft");
		sql.params().set("dif", dif).set("lft", lft);
		dao().execute(sql);

		int pid = data.getParentId();
		int pos = data.getPosition();
		// Update position of siblings below the deleted node
		sql = Sqls
				.create("UPDATE SYSTEM_JSTREENODE SET POSITION = POSITION - 1 WHERE PARENTID = @pid AND POSITION > @pos");
		sql.params().set("pid", pid).set("pos", pos);
		dao().execute(sql);
		return true;
	}

	public boolean move(int id, int refId, int position, boolean isCopy) {
		if (refId == 0 || id == 1) {
			return false;
		}
		List<Sql> sqls = new ArrayList<Sql>();
		JsTreeNode node = this.getNode(id);
		List<JsTreeNode> nChildren = this.getChildren(id, false);
		JsTreeNode refNode = this.getNode(refId);
		List<JsTreeNode> rChildren = this.getChildren(refId, false);

		int nDif = 2;
		List<Integer> nodeIds = new ArrayList<Integer>();

		if (node != null) {
			List<JsTreeNode> nAllChildren = this.getChildren(id, true);
			for (JsTreeNode aNode : nAllChildren) {
				nodeIds.add(aNode.getId());
			}
			// TODO: should be !$is_copy && , but if copied to self - screws some right indexes
			if (nodeIds.contains(refId)) {
				return false;
			}
			nDif = node.getRight() - node.getLeft() + 1;
		}
		int rChildrenCount = rChildren.size();
		if (position >= rChildrenCount) {
			position = rChildrenCount;
		}

		Sql sql;
		// Not creating or copying - old parent is cleaned
		if (node != null && isCopy == false) {
			sql = Sqls
					.create("UPDATE SYSTEM_JSTREENODE SET POSITION = POSITION - 1 WHERE PARENTID = @parentid AND POSITION > @position");
			sql.params().set("pid", node.getParentId()).set("pos", node.getPosition());
			sqls.add(sql);
			sql = Sqls.create("UPDATE SYSTEM_JSTREENODE SET LEFT = LEFT - @nDif WHERE LEFT > @right");
			sql.params().set("nDif", nDif).set("right", node.getRight());
			sqls.add(sql);
			sql = Sqls
					.create("UPDATE SYSTEM_JSTREENODE SET RIGHT = RIGHT - @nDif WHERE RIGHT > @left AND ID NOT IN @nodeIds");
			sql.params().set("nDif", nDif).set("left", node.getLeft()).set("left", nodeIds.toString());
			sqls.add(sql);
		}
		// Preparing new parent
		sql = Sqls
				.create("UPDATE SYSTEM_JSTREENODE SET POSITION = POSITION + 1 WHERE PARENTID > @refId AND POSITION >= @position AND ID NOT IN @nodeIds");
		sql.params().set("refId", refId).set("position", position).set("nodeIds", nodeIds.toString());
		sqls.add(sql);

		int refInd = refId == 0 ? rChildren.get(rChildrenCount - 1).getRight() + 1 : refNode.getRight();
		refInd = refInd > 1 ? refInd : 1;

		int self = (node != null && !isCopy && node.getParentId() == refId && position > node.getPosition()) ? 1 : 0;
		for (JsTreeNode aNode : rChildren) {
			if (aNode.getPosition() - self == position) {
				refInd = aNode.getLeft();
				break;
			}
		}
		if (node != null && !isCopy && node.getLeft() < refInd) {
			refInd -= nDif;
		}

		String sqlStr = "UPDATE SYSTEM_JSTREENODE SET LEFT = LEFT + @nDif WHERE LEFT >= @refInd";
		if (!isCopy) {
			sqlStr += " AND ID NOT IN @nodeIds";
		}
		sql = Sqls.create(sqlStr);
		sql.params().set("nDif", nDif).set("nodeIds", nodeIds.toString());
		sqls.add(sql);
		sqlStr = "UPDATE SYSTEM_JSTREENODE SET RIGHT = RIGHT + @nDif WHERE RIGHT >= @refInd";
		if (!isCopy) {
			sqlStr += " AND ID NOT IN @nodeIds";
		}
		sql = Sqls.create("sqlStr");
		sql.params().set("nDif", nDif).set("nodeIds", nodeIds.toString());
		sqls.add(sql);

		int lDif = refId == 0 ? 0 : refNode.getLevel() + 1;
		int iDif = refInd;
		if (node != null) {
			lDif = node.getLevel() - refNode.getLevel() + 1;
			iDif = node.getLeft() - refInd;
			if (isCopy) {
				sql = Sqls
						.create("INSERT INTO SYSTEM_JSTREENODE (PARENTID,POSITION,LEFT,RIGHT,LEVEL) SELECT 	@refId )");
				sql.params().set("refId", refId).set("position", position).set("nodeIds", nodeIds.toString());
				sqls.add(sql);

			}
		}

		//		$ldif = $ref_id == 0 ? 0 : $ref_node[$this->fields["level"]] + 1;
		//		$idif = $ref_ind;
		//		if($node !== false) {
		//			$ldif = $node[$this->fields["level"]] - ($ref_node[$this->fields["level"]] + 1);
		//			$idif = $node[$this->fields["left"]] - $ref_ind;
		//			if($is_copy) {
		//				$sql[] = "" . 
		//					"INSERT INTO `".$this->table."` (" . 
		//						"`".$this->fields["parent_id"]."`, " . 
		//						"`".$this->fields["position"]."`, " . 
		//						"`".$this->fields["left"]."`, " . 
		//						"`".$this->fields["right"]."`, " . 
		//						"`".$this->fields["level"]."`" . 
		//					") " . 
		//						"SELECT " . 
		//							"".$ref_id.", " . 
		//							"`".$this->fields["position"]."`, " . 
		//							"`".$this->fields["left"]."` - (".($idif + ($node[$this->fields["left"]] >= $ref_ind ? $ndif : 0))."), " . 
		//							"`".$this->fields["right"]."` - (".($idif + ($node[$this->fields["left"]] >= $ref_ind ? $ndif : 0))."), " . 
		//							"`".$this->fields["level"]."` - (".$ldif.") " . 
		//						"FROM `".$this->table."` " . 
		//						"WHERE " . 
		//							"`".$this->fields["id"]."` IN (".implode(",", $node_ids).") " . 
		//						"ORDER BY `".$this->fields["level"]."` ASC";
		//			}
		//			else {
		//				$sql[] = "" . 
		//					"UPDATE `".$this->table."` SET " . 
		//						"`".$this->fields["parent_id"]."` = ".$ref_id.", " . 
		//						"`".$this->fields["position"]."` = ".$position." " . 
		//					"WHERE " . 
		//						"`".$this->fields["id"]."` = ".$id;
		//				$sql[] = "" . 
		//					"UPDATE `".$this->table."` SET " . 
		//						"`".$this->fields["left"]."` = `".$this->fields["left"]."` - (".$idif."), " . 
		//						"`".$this->fields["right"]."` = `".$this->fields["right"]."` - (".$idif."), " . 
		//						"`".$this->fields["level"]."` = `".$this->fields["level"]."` - (".$ldif.") " . 
		//					"WHERE " . 
		//						"`".$this->fields["id"]."` IN (".implode(",", $node_ids).") ";
		//			}
		//		}
		//		else {
		//			$sql[] = "" . 
		//				"INSERT INTO `".$this->table."` (" . 
		//					"`".$this->fields["parent_id"]."`, " . 
		//					"`".$this->fields["position"]."`, " . 
		//					"`".$this->fields["left"]."`, " . 
		//					"`".$this->fields["right"]."`, " . 
		//					"`".$this->fields["level"]."` " . 
		//					") " . 
		//				"VALUES (" . 
		//					$ref_id.", " . 
		//					$position.", " . 
		//					$idif.", " . 
		//					($idif + 1).", " . 
		//					$ldif. 
		//				")";
		//		}
		//		foreach($sql as $q) { $this->db->query($q); }
		//		$ind = $this->db->insert_id();
		//		if($is_copy) $this->_fix_copy($ind, $position);
		//		return $node === false || $is_copy ? $ind : true;

		return false;
	}
}
