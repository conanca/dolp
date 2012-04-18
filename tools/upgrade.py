#! /usr/bin/env python
# -*- coding: utf-8 -*-

# 自动升级 dolpin hotel 的基盘的脚本

import re,os,shutil

class UpgradeDolp:

    def __init__(self):
        self.root_path = '../'
        self.base_path = self.root_path + 'dolp-base/'
        self.hotel_path = self.root_path + 'demo/dolpinhotel/'
        #移除旧的目录时，需排除的目录
        self.retain_dirs = set(['WebContent/hotel/','WebContent/js-hotel/'])
        #移除旧的目录时，需排除的文件
        self.retain_files = set(['config/init_hotel_h2.sql','WebContent/home.html','WebContent/WEB-INF/web.xml'])
        #需替换文件内容中部分文本的文件
        self.repl_cont_txt_file = set(['build.xml','config/dao.js','config/ioc.dolpbase.js','config/logback.xml'])
        #不做任何操作的文件或目录
        self.ignores = set(['build/','dist/','.settings/','src/','.classpath','.project'])
        self.no_delete_dirs = set()

    def base_build(self):
        ''' 编译基盘 '''
        os.system('ant -buildfile "{}dolp-base/build.xml"'.format(self.root_path))
        is_dist_dir_exists = os.path.exists('{}dolp-base/dist/'.format(self.root_path))
        print is_dist_dir_exists
        return is_dist_dir_exists

    def judge_file(self,file_path):
        ''' 处理指定的的文件，判断其是否需要被替换 '''
        file_path = file_path.replace('\\','/')
        #保留目录下的、保留文件、替换文本的文件->保留；其他->将被替换
        file_path = file_path[len(self.hotel_path):]
        file_dir = file_path[:file_path.rfind('/') + 1]
        if file_dir in self.retain_dirs:
            print '=1=retain this file==' + file_path
            return
        if file_path in self.retain_files.union(self.repl_cont_txt_file):
            print '=2=repalce the content text of this file==' + file_path
            return
        if file_path in self.ignores:
            print '=3=retain this file==' + file_path
            return
        print '==this file will be replaced==' + file_path
        self.replace_file(file_path)
        pass

    def init_no_delete_dirs(self):
        ''' 获取不能被直接删除的目录 '''
        for retain_file in self.retain_files:
            last_index = retain_file.rfind('/')
            if last_index == -1:
                break
            dir = retain_file[:last_index]
            self.no_delete_dirs.add(dir + '/')
        self.no_delete_dirs = self.no_delete_dirs.union(self.retain_dirs)
        print self.no_delete_dirs

    def walk_hotel_path(self):
        ''' 遍历 hotel 项目目录 '''
        for cur_dir_path,dirs,files in os.walk(self.hotel_path):
            cur_dir_path1 = cur_dir_path[len(self.hotel_path):].replace('\\','/') + '/'
            # 指定忽略的目录 -> 不做任何操作
            if cur_dir_path1[:cur_dir_path1.find('/')]+'/' in self.ignores:
                print '==retain this dir==' + cur_dir_path1
                continue
            # 根目录和其他不能被删除的目录 -> 迭代下面的文件及目录
            if cur_dir_path1 == '/' or cur_dir_path1 in self.no_delete_dirs:
                # 如果是文件就执行replace_file方法
                for f in files:
                    file_path = os.path.join(cur_dir_path,f)
                    self.judge_file(file_path)
            # 其他目录 -> 将被替换
            else:
                print '==this dir will be replaced==' + cur_dir_path1
                self.replace_dir(cur_dir_path1)
                pass

    def replace_dir(self,dir_path):
        ''' dolpinhotle 项目目录下，指定的目录替换为基盘对应的目录 '''
        shutil.rmtree(os.path.join(self.hotel_path,dir_path))
        shutil.copytree(os.path.join(self.base_path,dir_path), os.path.join(self.hotel_path,dir_path))
        pass

    def replace_file(self,file_path):
        ''' dolpinhotle 项目目录下，指定的目录替换为基盘对应的文件 '''
        shutil.copy(os.path.join(self.base_path,file_path), os.path.join(self.hotel_path,file_path))
        pass

    def add_dolp_jar(self):
        shutil.copy(os.path.join(self.base_path,'dist/dolp.jar'),os.path.join(self.hotel_path,'WebContent/WEB-INF/lib/'))

if __name__ == '__main__':
    upgrade = UpgradeDolp()
    upgrade.base_build()
    upgrade.init_no_delete_dirs()
    upgrade.walk_hotel_path()
    upgrade.add_dolp_jar()
