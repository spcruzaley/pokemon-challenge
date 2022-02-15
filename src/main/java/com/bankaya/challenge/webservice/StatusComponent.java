package com.bankaya.challenge.webservice;

import com.bankaya.challenge.webservice.gen.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusComponent {

    public Status getDefaultStatus() {
        Status status = new Status();
        status.setMessage("Successful call to WebService");

        return status;
    }

    public Status setDefaultStatus(String message) {
        Status status = new Status();
        status.setMessage(message);

        return status;
    }

}
