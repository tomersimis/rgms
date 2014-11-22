package pages.Conferencia

import geb.Page
import pages.GetPageTitle
import rgms.tool.TwitterTool

class ConferenciaShowPage extends Page {
	static url = "visit/show/1"

	static at = {
		//title ==~ /Ver Conferencia/
		GetPageTitle gp = new GetPageTitle()
		def currentConferencia = gp.msg("default.conference.label")
		def currentTitle = gp.msg("default.show.label", [currentConferencia])
		title ==~ currentTitle
	}

def deleteConferencia() {
assert withConfirm(true) { $("form").find('input', class: 'delete').click() }
}

def editConferencia() {
$("form").find('a', class: 'edit').click()
}

def addTwitter(String conferencia) {
TwitterTool.addTwitterHistory(conferencia, null)
}

//#if ($Twitter)
	def clickOnTwitteIt(String login, pw) {
	$("#button_twitter").click()
	//$("#password").text = login
	//$("#username_or_email").text = pw
	//$("input", type:"submit", class:"button selected submit", value:"Entrar e Tweetar").click()
	//<input type="submit" class="button selected submit" value="Entrar e Tweetar">
	}
//#end
}