package com.page.objects;

import org.openqa.selenium.By;

public class ObjDashBoard {
	
	public static By caseIdFilter=By.xpath("//div//div[.='Dossier-ID']/../..//*[@id='pui_filter']");
	public static By rebutTrigFilter=By.xpath("//div//div[.='Rebuttable Trigger']");
	public static By filterOverlay=By.cssSelector("#_popOversContainer>#po0");
	public static By searchText=By.cssSelector("input[name$='SearchText']");
	public static By btnApply=By.xpath("//button[.='Toepassen']");
	public static By dashboardTable=By.cssSelector(".gridTable[pl_prop_class='Assign-WorkBasket']>tbody>tr.oddRow");
	public static By noItems=By.xpath("//tr[@id='Grid_NoResults']//div[contains(.,'Geen items')]");
	public static By casePat=By.xpath("//tr[@id='PReportPage$ppxResults$l3']");
	public static By secDate=By.xpath("//td[@data-attribute-name='Geplande datum']");
	public static By primDate=By.xpath("//div/span[@data-test-id='201507210538070209958']");
	
	
	public static String newTab="//li[@role='tab' and contains(@title,'<signalcase>')]";
	
	//status
	public static By caseStatus=By.xpath("//div/span[.='Status']/../..//span[@class='standard_small']");
	public static By rebTrig=By.xpath("//div/span[.='Rebuttable Trigger']/../..//span[@class='standard_small']");
	//public static By samDsierCrtion=By.xpath("//div/span[.='Status']/../..//span[.='Open-SamDossierCreation']");
	//public static By resolvedNotRebutted=By.xpath("//div/span[.='Status']/../..//span[.='Resolved-NotRebutted']");
	
	
	
	//PCP cases
	public static By gadgetFrame=By.cssSelector("#PegaGadget0Ifr,#PegaGadget1Ifr,#PegaGadget2Ifr");
	public static String groupItems="//h3[.='<groupItem>' and @class='layout-group-item-title']";
	public static By btnVerzenden=By.cssSelector("a.RaboOrange");
	public static By closeTabs=By.cssSelector("span#close");
	public static By actieButton=By.xpath("//a[@data-test-id='20170927100430055317911']");
	public static By verzendBtn=By.xpath("//button[@id='ModalButtonSubmit']");
	public static By newDosBtn=By.linkText("Nieuw Dossier");
	public static By teamBtn=By.cssSelector("span[tabtitle='TeamBoard']>span");
	public static By newCaseBtn=By.xpath("//span[@tabtitle='New Cases']//*[@inanchor]");
	public static By newCaseBtnSub=By.cssSelector("button[data-click*='CreateManualRebuttableSignalCase']");
	public static By wissenBtn=By.xpath("//button[.='Wissen']");
	public static By searchBtn=By.xpath("//button[.='Zoeken']");
	public static By achterNaam=By.cssSelector("input[name$='Achternaam']");
	public static By signalType=By.xpath("//select[@name='$PpyDisplayHarness$pSignalType']");
	public static By dateSignal=By.xpath("//input[@name='$PSearchNewCases$pOtherSignEventDate']");
	public static By loanNumber=By.cssSelector("input[name$='Agreementnumber']");
	public static By postalCode=By.cssSelector("input[name$='Postcode']");
	public static By houseNumber=By.cssSelector("input[name$='Huisnummer']");
	public static By birthDay=By.cssSelector("input[name$='Geboortedatum']");
	
	
	//information message
	//public static By inforMsg=By.cssSelector("informationmessage_dataLabelRead");
	public static By inforMsg=By.xpath("//*[contains(@class,'informationmessage_dataLabelRead')]");
	public static By errorMsg=By.xpath("//div[.='Geen resultaten gevonden']");
	public static By submMsg=By.xpath("//div[.='De case is aangemaakt']");
	public static By refreshIcon=By.linkText("Vernieuwen");
	public static By stopBtn=By.linkText("Stoppen");
	
	public static By samManagedTrue=By.cssSelector("label[for='IsSAMManagedtrue']");
	public static By samManagedFalse=By.cssSelector("label[for='IsSAMManagedfalse']");

	public static By assessMentReason=By.cssSelector("div[data-ui-meta*='ResultReasonDesc']>div>span");
	
	public static By assessMentReasonCons=By.xpath("//span[@data-test-id='20171025124007076522655']");
	
	public static By hypotheekBedrag=By.xpath("//label[contains(.,'Ja')]");
	
	public static By hypotheekBedragCons=By.cssSelector("label[for='pyWorkPageQuestionnaireMainQuestionList1AnswerOVERIGBNPQ1A2']");
	
	public static By eventDate=By.cssSelector("div[data-ui-meta*='OtherSignEventDate']>span");
	
	
	//1st 4 mandatory buttons
	public static By mandatory1=By.xpath("//div//span[contains(.,'Wordt een betaalachterstand van 100 euro of meer')]/../../../following-sibling::div//label[contains(.,'Ja')]");
	public static By mandatory2=By.xpath("//div//span[contains(.,'Heeft de faciliteit een betaalachterstand die')]/../following-sibling::div//label[contains(.,'Ja')]");
	public static By mandatory3=By.xpath("//div//span[contains(.,'ruimte voor de komende 3 maanden onvoldoende')]/../following-sibling::div//label[contains(.,'Ja')]");
	public static By mandatory4=By.xpath("//div//span[contains(.,'Wordt een onderhandse of gedwongen openbare verkoop')]/../../../following-sibling::div//label[contains(.,'Ja')]");
	
	
	//dpd30 properties
	public static By mandatory5=By.xpath("//span[contains(.,'Is betaalprobleem structureel')]/../following-sibling::div//label[contains(.,'Ja')]");
	public static By mandatory6=By.xpath("//span[contains(.,'Duurt oplossen betaalprobleem langer dan 90 dagen')]/../following-sibling::div//label[contains(.,'Ja')]");

	
	
	//rebute
	public static By refute=By.cssSelector("label[for$='Rebutted']");
	public static By Notrefute=By.cssSelector("label[for$='NotRebutted']");
	
	public static By doNotJudge=By.cssSelector("label[for$='NotAssessed']");
	
	
	public static By slctRsonCode=By.cssSelector("select[name$='ResultReasonCode']");
	
	public static By slctTeam=By.xpath("//select[@name='$PpyDisplayHarness$ppySearchText']");
	public static By enterReason=By.xpath("//*[@name='$PpyWorkPage$pComments']");
	
	//beslag options
	public static By executoriaal=By.cssSelector("label[for$='EXECCLAIM']");
	public static By conservatoir=By.cssSelector("label[for$='CONCLAIM']");
    public static By verkeerdSignaal=By.cssSelector("label[for$='WRONGSIGNALCLAIM']");
    public static By selectDossier=By.cssSelector("input~[name$='pySelected']");
	
	
	public static By claimAmount=By.cssSelector("input[name$='ClaimAmount']");
	
	public static By productEn=By.xpath("//h3[contains(.,'Producten')]");
	public static By lstLoanNo=By.xpath("//td[@data-attribute-name='Leningdeel']");
	
	public static By collateralTab=By.xpath("//h3[contains(.,'Onderpand')]");
	public static By collObjTypeDesc=By.xpath("//td[@data-attribute-name='Soort registergoed']");

	public static By bkrTab=By.xpath("//h3[contains(.,'BKR')]");
	
	public static By bkrContRel=By.xpath("//div[@node_name='BKRContractRelation']");
	
	public static By overlijdenContRel=By.xpath("//div[@class='content-item content-label item-1 flex flex-row informationmessage_dataLabelRead dataLabelRead informationmessage_dataLabelRead']");
			
}
