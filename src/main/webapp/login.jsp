<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
table.yui { 

border-collapse:collapse; 

border-spacing: 0; 
}
.wwFormTable
{
margin-left:-6px;
}
table.wwFormTable tr{
margin: 0 0 10px 0;
}
</style>

<span id="perror" style="display:none"></span>
<script type="text/javascript">
function validateLength()
{
	if(document.getElementById("login_password").value.length < 6)
		{
		alert('1');
			document.getElementById("errorMessage").innerHTML = "<span class="+"'error'"+">Please provide correct User Name and Password.</span>";  		
		}
	else if(document.getElementById("login_password").value.length > 10)
	{
		alert('2');
		document.getElementById("errorMessage").innerHTML = "<span class="+"'error'"+">Please provide correct User Name and Password.</span>";  		
	}
	/* else if(document.getElementById("login_password").value.search("^\w*(?=\w*\d)(?=\w*[A-Za-z])\w*$")==-1)
		{
		alert('3');
			document.getElementById("errorMessage").innerHTML = "<span class="+"'error'"+">Please provide correct User Name and Password.</span>";
		} */
	else
		{
		alert('4');
			document.loginform.submit();
		}
}
</script>
<form:form id="login" name="loginform" action="/SapeStore/login" method="POST" commandName="userlogin">
<table class="wwFormTable">
   <fieldset>
	 <tr>
    	<td class="tdLabel"></td>
    	<td><label id="login_" style="font-size: 16px;font-weight: bold">User Name<span class="required">*</span></label></td>
	  </tr>
     <tr>
   		 <td class="tdLabel"></td>
    	<td><input type="text" name="userId" value="" id="login_userId" placeholder="User Name" required/></td>
	 </tr>
     <tr>
    	 <td class="tdLabel"></td>
    	 <td><label id="login_" style="font-size: 16px;font-weight: bold">Password<span class="required">*</span></label>
      <a class="forgotPassword inline" title="Forgot Password"  href="#forgotPassword">Forgot password?</a>
      </td>
	</tr>
    <tr>
  		<td class="tdLabel"></td>
    	<td><input type="password" name="password" id="login_password" placeholder="Password" required/></td>
	</tr>
			    <tr>
  					<td class="tdLabel"></td>
    				<td id="errorMessage"></td>
				</tr>
    <tr>
    	<td colspan="2"><div align="center"><input type="button" id="login_0" onclick="validateLength()" value="Login" class="lightButton"/>
		</div>
		</td>
	</tr>
    <tr>
    	<td class="tdLabel"></td>
    	<td><label id="login_">Don't have an account yet?</label></td>
	</tr>
    <tr>
   		 <td colspan="2"><div align="center"><input type="submit" id="login_2" value="Register" class="darkButton"/>
		</div>
		</td>
	</tr>
    </fieldset>
  </table>
  </form:form>


