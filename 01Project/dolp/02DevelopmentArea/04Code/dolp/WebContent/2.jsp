<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=abcdefg" type="text/javascript"></script>
<script type="text/javascript">
function initialize() {
      if (GBrowserIsCompatible()) {
        var map = new GMap2(document.getElementById("map_canvas"));
        map.setCenter(new GLatLng(37.4419, -122.1419), 13);
      }
    }
</script>
<div id="map_canvas" style="width: 500px; height: 300px"></div>