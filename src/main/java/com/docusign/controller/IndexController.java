package com.docusign.controller;

import com.docusign.controller.examples.DSHelper;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class IndexController {

    @RequestMapping(method = RequestMethod.POST, path = "/pushToDocuSign")
    public ResponseEntity<String> sendToDocuSign(@RequestBody String payload) {
        DSHelper helper = new DSHelper();
        JSONObject request = new JSONObject(payload);
        String url= helper.Send_Invoice_To_Docusign(request);
        //String url = helper.send_invoice_to_docusign(request.getString("doc"), request.getString("signerEmailId"), request.getString("docuSignEmailSubject"), request.getString("docuSignEmailBody"), request.getInt("txnId"));
        return new ResponseEntity<String>(url, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/EmbedView")
    public ResponseEntity<String> EmbedViewOfDocuSign(@RequestBody String payload) {
        DSHelper helper = new DSHelper();
        JSONObject request = new JSONObject(payload);
        String url= helper.Embed_Console_View(request);
        //String url = helper.send_invoice_to_docusign(request.getString("doc"), request.getString("signerEmailId"), request.getString("docuSignEmailSubject"), request.getString("docuSignEmailBody"), request.getInt("txnId"));
        return new ResponseEntity<String>(url, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/callbackFromDocuSign")
    public ResponseEntity<String> callbackFromDocuSign( @RequestParam(name = "event") String event, @RequestParam(name = "envelopeId") String envelopeId) {
        System.out.println("Docusign Callback Received: " + event + " : " + envelopeId);
        return new ResponseEntity<String>("Docusign Callback Received: " + event + " : " + envelopeId , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/health")
    public String healthCheck() {
        return "healthy";
    }
}
