$(document).ready(function () {
	$('.searchCategory').click(function(){
	var searchValue =  this.id;
	$('#searchText').val(searchValue);
	$('#topSearch').submit();
	}); 
});
function refererLink(link)
{
	$.cookie("referer", $(location).attr('href'),{'path':'/'});	
	window.location = link;
}