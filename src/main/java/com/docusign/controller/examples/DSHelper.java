package com.docusign.controller.examples;

import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.api.UsersApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.model.*;
import com.docusign.esign.model.UserSettingsInformation;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DSHelper {

    static final String accountId = "10523463";
    static final String Oauth= "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjY4MTg1ZmYxLTRlNTEtNGNlOS1hZjFjLTY4OTgxMjIwMzMxNyJ9.eyJUb2tlblR5cGUiOjUsIklzc3VlSW5zdGFudCI6MTU5NDg4MjA5OCwiZXhwIjoxNTk0OTEwODk4LCJVc2VySWQiOiIwMWVmYjZjZS1iMjRjLTQ2ZGUtYTQxZS1hNmNmYjI1NWU1ZmQiLCJzaXRlaWQiOjEsInNjcCI6WyJzaWduYXR1cmUiLCJjbGljay5tYW5hZ2UiLCJvcmdhbml6YXRpb25fcmVhZCIsInJvb21fZm9ybXMiLCJncm91cF9yZWFkIiwicGVybWlzc2lvbl9yZWFkIiwidXNlcl9yZWFkIiwidXNlcl93cml0ZSIsImFjY291bnRfcmVhZCIsImRvbWFpbl9yZWFkIiwiaWRlbnRpdHlfcHJvdmlkZXJfcmVhZCIsImR0ci5yb29tcy5yZWFkIiwiZHRyLnJvb21zLndyaXRlIiwiZHRyLmRvY3VtZW50cy5yZWFkIiwiZHRyLmRvY3VtZW50cy53cml0ZSIsImR0ci5wcm9maWxlLnJlYWQiLCJkdHIucHJvZmlsZS53cml0ZSIsImR0ci5jb21wYW55LnJlYWQiLCJkdHIuY29tcGFueS53cml0ZSJdLCJhdWQiOiJmMGYyN2YwZS04NTdkLTRhNzEtYTRkYS0zMmNlY2FlM2E5NzgiLCJhenAiOiJmMGYyN2YwZS04NTdkLTRhNzEtYTRkYS0zMmNlY2FlM2E5NzgiLCJpc3MiOiJodHRwczovL2FjY291bnQtZC5kb2N1c2lnbi5jb20vIiwic3ViIjoiMDFlZmI2Y2UtYjI0Yy00NmRlLWE0MWUtYTZjZmIyNTVlNWZkIiwiYW1yIjpbImludGVyYWN0aXZlIl0sImF1dGhfdGltZSI6MTU5NDg4MjA5NCwicHdpZCI6Ijg0MGM0ZTc1LTgyZDktNGFmZi1hNWQ0LTE3OWMwZmVjZWRjYSJ9.hx40od22gtchyNuYf_CRauSG3IEySKi0gsSo1xrDWuRQEDqEhRMphwzSqSr0XOXXYpVEGKzW44pvwmi3yF1ALwOcnJyaaA8BFiV-boBM1T9ANhRJFAV6WKZ5gmG7EQ4_2ELNbf-_VVudEBLiMbiYZD-RsQTFKPItLdsyz9G2_IL0L1dn6eUr4YlSX6hNVW6tv5IJvRr4CzRYTHgF1rXs52qXXXVfa3OcEPi0sOAgB5LyuACI7OwtB7em7NIucGfYgAOIXM9p3i4U7m44gnwWfHrnaGac06f9Q5gCT6pheK1s8W4fzXoVQeRCUqGkjS7kMV7GhIYfX2B67YTMkxukYg";
    static final String APIHeader= "Bearer " + Oauth;
    static final String WebhookEndpoint = "https://e2e.api.intuit.com/appconnect/connector/506809/api/estimatetoinvoice.json?token=rnb48flm0y7dpl3&txnid=";
    static final String IntuitKey = "&intuit_apikey=preprdakyresMdw2YDuESHYPb3MkwDH4FWGAh0wc";
    static final String envelopeId = "f5305e3e-0716-41ac-8620-e699b79f0b4b";
    static final String returnUrl = "https://qbo.intuit.com";

    public String Send_Invoice_To_Docusign(JSONObject request) {
        String redirectUrl = null;
        try {
            EnvelopeDefinition env = makeEnvelope(request);
            ApiClient apiClient = new ApiClient("https://demo.docusign.net/restapi");
            apiClient.addDefaultHeader("Authorization", APIHeader);
            EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);
            EnvelopeSummary results = envelopesApi.createEnvelope(accountId, env);
            ReturnUrlRequest returnUrlRequest = new ReturnUrlRequest();
            ViewUrl results1 = envelopesApi.createEditView(accountId, results.getEnvelopeId(), returnUrlRequest);
            redirectUrl = results1.getUrl();
           } catch (Exception e) {
            e.printStackTrace();
        }
        return redirectUrl;
    }

    public String Embed_Console_View(JSONObject request)
    {
        String redirectUrl = null;
        try {
            ApiClient apiClient = new ApiClient("https://demo.docusign.net/restapi");
            apiClient.addDefaultHeader("Authorization", APIHeader);
            EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);
            ConsoleViewRequest consoleViewRequest = new ConsoleViewRequest();
            consoleViewRequest.setEnvelopeId(envelopeId);
            consoleViewRequest.setReturnUrl(returnUrl);
            ViewUrl consoleView = envelopesApi.createConsoleView(accountId, consoleViewRequest);
            System.out.println("Console URL: " + consoleView.getUrl());
            redirectUrl = consoleView.getUrl();
           } catch (Exception e) {
            e.printStackTrace();
        }
        return redirectUrl;
    }

    private SignHere addSignHeretab()
    {
        SignHere signHere1 = new SignHere();
        signHere1.setDocumentId("1");
        signHere1.setPageNumber("1");
        signHere1.setRecipientId("1");
        signHere1.setAnchorString("Accepted By");
        signHere1.setAnchorUnits("pixels");
        signHere1.setAnchorXOffset("150");
        signHere1.setAnchorYOffset("0");
        return signHere1;
    }

    private DateSigned addDateSignedtab()
    {
        DateSigned dateSigned = new DateSigned();
        dateSigned.setDocumentId("1");
        dateSigned.setPageNumber("1");
        dateSigned.setRecipientId("1");
        dateSigned.setAnchorString("Date");
        dateSigned.setAnchorCaseSensitive("true");
        dateSigned.setAnchorUnits("pixels");
        dateSigned.setAnchorXOffset("60");
        dateSigned.setAnchorYOffset("0");
        return dateSigned;
    }

    private Approve addApprove()
    {
        Approve approve=new Approve();
        approve.setDocumentId("1");
        approve.setPageNumber("1");
        approve.setRecipientId("1");
        approve.setAnchorString("Accepted By");
        approve.setButtonText("Approve");
        approve.setAnchorCaseSensitive("true");
        approve.setAnchorUnits("pixels");
        approve.setAnchorXOffset("460");
        approve.setAnchorYOffset("0");
        return approve;
    }

    private Decline addDecline()
    {
        Decline decline=new Decline();
        decline.setDocumentId("1");
        decline.setPageNumber("1");
        decline.setRecipientId("1");
        decline.setAnchorString("Accepted By");
        decline.setButtonText("Decline");
        decline.setAnchorCaseSensitive("true");
        decline.setAnchorUnits("pixels");
        decline.setAnchorXOffset("460");
        decline.setAnchorYOffset("35");
        return decline;

    }

    private EventNotification addEventNotification(String WebhookListener)
    {
        EventNotification eventNotification = new EventNotification();
        eventNotification.setUrl(WebhookListener);
        eventNotification.setIncludeDocuments("true");
        eventNotification.setLoggingEnabled("true");
        eventNotification.setIncludeEnvelopeVoidReason("true");
        eventNotification.setIncludeSenderAccountAsCustomField("true");
        eventNotification.setRequireAcknowledgment("true");
        List<EnvelopeEvent> envelopeEvents = new ArrayList<>();
        EnvelopeEvent envelopeEvent = new EnvelopeEvent();
        envelopeEvent.setEnvelopeEventStatusCode("Completed");
        envelopeEvent.setIncludeDocuments("true");
		envelopeEvents.add(envelopeEvent);
        EnvelopeEvent envelopeEvent2 = new EnvelopeEvent();
        envelopeEvent2.setEnvelopeEventStatusCode("Declined");
        envelopeEvent2.setIncludeDocuments("true");
        envelopeEvents.add(envelopeEvent2);
        eventNotification.setEnvelopeEvents(envelopeEvents);
        return eventNotification;
    }

    private Signer addSigner(String signerName, String signerEmail)
    {
        Signer signer1 = new Signer();
        signer1.setEmail(signerEmail);
        signer1.setName(signerName);
        signer1.setRecipientId("1");
        signer1.setRoutingOrder("1");
        return signer1;

    }

    private EnvelopeDefinition makeEnvelope(JSONObject request) throws IOException {
    
        String signerEmail = request.getString("signerEmailId");
        String signerName = request.getString("signerName");
        String status = ("created");
        String EmailSubject = request.getString("docuSignEmailSubject");
        String EmailBody = request.getString("docuSignEmailBody");
        String pdf = request.getString("doc");
        String TxnId = Integer.toString(request.getInt("txnId"));
        EnvelopeDefinition env = new EnvelopeDefinition();
        env.setEmailSubject(EmailSubject); 
        env.setEmailBlurb(EmailBody);
        Document doc = new Document();
        doc.setDocumentBase64(pdf);
        doc.setName("Transaction Summary");
        doc.setFileExtension("pdf");
        doc.setDocumentId("1");
        env.setDocuments(Arrays.asList(doc));

        Signer signer1 = addSigner(signerName, signerEmail);

        SignHere signHere1 = addSignHeretab();
        DateSigned dateSigned = addDateSignedtab();
        Approve approve = addApprove();
        Decline decline = addDecline();

        Tabs signer1Tabs = new Tabs();
        signer1Tabs.setSignHereTabs(Arrays.asList(signHere1));
        signer1Tabs.setDateSignedTabs(Arrays.asList(dateSigned));
        signer1Tabs.setApproveTabs(Arrays.asList(approve));
        signer1Tabs.setDeclineTabs(Arrays.asList(decline));
        signer1.setTabs(signer1Tabs);
        
        Recipients recipients = new Recipients();
        recipients.setSigners(Arrays.asList(signer1));
        env.setRecipients(recipients);
        env.setStatus(status);

        EventNotification eventNotification = addEventNotification(WebhookEndpoint + TxnId + IntuitKey);
        env.setEventNotification(eventNotification);
        return env;

    }
}
