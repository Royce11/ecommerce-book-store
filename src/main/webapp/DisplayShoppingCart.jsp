<%-- DisplayShoppingCart.jsp  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">

.pg-normal { 
	color: #de2728;
	font-size: 14px;
	cursor: pointer;
	padding: 2px 4px 2px 4px;
	font-weight: bold 
}

.pg-selected { 
	color: #fff;
	font-size: 14px;
	background: #de2728;
	padding: 2px 4px 2px 4px;
	font-weight: bold 
}

table.yui { 

border-collapse:collapse; 

font-size:small; 
}

table.yui td { 
padding: 5px; 

}

table.yui .even { 
background-color: #EEE8AC; 
}

table.yui .odd { 
background-color: #F9FAD0; 
}

table.yui th { 

padding: 5px; 
height: auto; 

}

table.yui th a { 
text-decoration: none; 
text-align: center; 
padding-right: 20px; 
font-weight:bold; 
white-space:nowrap; 
}

table.yui tfoot td { 
 
background-color:#E1ECF9; 
}

table.yui thead td { 
vertical-align:middle; 
background-color:#E1ECF9; 
border:none; 
}

table.yui thead .tableHeader { 
font-size:larger; 
font-weight:bold; 
}

table.yui thead .filter { 
text-align:right; 
}

table.yui tfoot { 
background-color:#E1ECF9; 
text-align:center; 
}

table.yui .tablesorterPager { 
padding: 10px 0 10px 0; 
}

table.yui .tablesorterPager span { 
padding: 0 5px 0 5px; 
}

table.yui .tablesorterPager input.prev { 
width: auto; 
margin-right: 10px; 
}

table.yui .tablesorterPager input.next { 
width: auto; 
margin-left: 10px; 
}

table.yui .pagedisplay { 
font-size:10pt; 
width: 30px; 
border: 0px; 
background-color: #E1ECF9; 
text-align:center; 
vertical-align:top; 
}
.homeAdmin #mainContent table{
 width: 99%;
 }
 #pageNavPosition{
 	float: right;
	padding-left: 774px;
	padding-bottom: 2em;
	padding-top: 0.5em;
	padding-right: 2px;
	padding-left: 740px;
	margin-right: 7px;
	}
</style>

<script type="text/javascript">

function Pager(tableName, itemsPerPage) {

this.tableName = tableName;

this.itemsPerPage = itemsPerPage;

this.currentPage = 1;

this.pages = 0;

this.inited = false;

this.showRecords = function(from, to) {

var rows = document.getElementById(tableName).rows;

// i starts from 1 to skip table header row

for (var i = 1; i < rows.length; i++) {

if (i < from || i > to)

rows[i].style.display = 'none';

else

rows[i].style.display = '';

}

}

this.showPage = function(pageNumber) {
		
if (! this.inited) {

alert("not inited");

return;

}



var oldPageAnchor = document.getElementById('pg'+this.currentPage);

oldPageAnchor.className = 'pg-normal';

this.currentPage = pageNumber;

var newPageAnchor = document.getElementById('pg'+this.currentPage);

newPageAnchor.className = 'pg-selected';

var from = (pageNumber - 1) * itemsPerPage + 1;

var to = from + itemsPerPage - 1;

this.showRecords(from, to);

}

this.prev = function() {

if (this.currentPage > 1)

this.showPage(this.currentPage - 1);

}

this.next = function() {

if (this.currentPage < this.pages) {

this.showPage(this.currentPage + 1);

}

}

this.init = function() {

var rows = document.getElementById(tableName).rows;

var records = (rows.length - 1);

this.pages = Math.ceil(records / itemsPerPage);

this.inited = true;

}

this.showPageNav = function(pagerName, positionId) {

if (! this.inited) {

alert("not inited");

return;

}
if(pages>1){
var element = document.getElementById(positionId);

var pagerHtml = '<span onclick="' + pagerName + '.prev();" class="pg-normal"> « Prev </span> ';

for (var page = 1; page <= this.pages; page++)

pagerHtml += '<span id="pg' + page + '" class="pg-normal" onclick="' + pagerName + '.showPage(' + page + ');">' + page + '</span> ';

pagerHtml += '<span onclick="'+pagerName+'.next();" class="pg-normal"> Next »</span>';

element.innerHTML = pagerHtml;
}
}

}

</script>
<form:form name="cart" action="displayShoppingCart" method="GET">
<h2>Your Cart</h2>
    <div class="scroller">
    <table id="tablepaging" class="yui">
<%-- Display the heading of the shoppingCart --%>
      <thead>
        <tr>
          <th>Item(s)</th>
          <th></th>
          <th>Qty.</th>
          <th>Price</th>
          <th>Subtotal</th>
          <th></th>
        </tr>
      </thead>
     
     
      <c:forEach items="${ShoppingCart.booksInCart}" var="current">
      <tbody>
        <tr>
       		
	          <td style="width:60px"><img src="${current.thumbPath}" width="81" height="112" alt="product name"></td>
	          <td>${current.bookTitle}</td>
	          <td><input type="text" name="quantity" id="book_quantity" value="${current.quantity}" size="2"/></td>
	          <td>$${current.bookPrice}</td>
	          <td>$${current.quantity * current.bookPrice}</td>
	          <td>
		          <input type="hidden" name= "item" value=""/>
		          <input type="button" value="Remove" class="darkButton">
	          </td>
        </tr>
	    
     </tbody>
	</c:forEach> 
    </table>
  </div>
  <div id="pageNavPosition"></div>
  <div> 
  		<p><span>Total Amount Payable : </span> $${ShoppingCart.totalPrice}</p><br>
  		
		<input type="button" value="Close &amp; Countinue Shopping" class="lightButton">
	    <input type="button" value="Checkout" class="lightButton checkout">
  </div>
  	<script type="text/javascript">
		var pager = new Pager('tablepaging', 10);
		pager.init();
		pager.showPageNav('pager', 'pageNavPosition');
		pager.showPage(1);
	</script>
  </form:form>