$(".header_ul_left>.glyphicon-map-marker").mouseover(function () {
	$(this).children("#beijing").show();
}).mouseout(function () {
	$(this).children("#beijing").hide();
})
$(".header_ul_right>.jingdong").mouseover(function () {
	$(this).children(".jingdong_ol").show();
}).mouseout(function () {
	$(this).children(".jingdong_ol").hide();
})
$(".header_ul_right>.fuwu").mouseover(function () {
	$(this).children(".fuwu_ol").show();
}).mouseout(function () {
	$(this).children(".fuwu_ol").hide();
})
$(".header_ul_right>.daohang").mouseover(function () {
	$(this).children(".daohang_ol").show();
}).mouseout(function () {
	$(this).children(".daohang_ol").hide();
})

