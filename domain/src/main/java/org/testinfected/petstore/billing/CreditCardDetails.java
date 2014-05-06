package org.testinfected.petstore.billing;

import java.io.Serializable;

import org.testinfected.petstore.validation.Constraint;
import org.testinfected.petstore.validation.NotNull;
import org.testinfected.petstore.validation.Valid;
import org.testinfected.petstore.validation.Validates;

public class CreditCardDetails extends PaymentMethod implements Serializable {

    private final CreditCardType cardType;
    private final Constraint<String> cardNumber;
    private final NotNull<String> cardExpiryDate;
    private final Valid<Address> billingAddress;


    public CreditCardDetails(CreditCardType cardType, String cardNumber, String cardExpiryDate, Address billingAddress) {
        this.cardType = cardType;
        this.cardNumber = Validates.both(Validates.notEmpty(cardNumber), Validates.correctnessOf(cardType, cardNumber));
        this.cardExpiryDate = Validates.notNull(cardExpiryDate);
        this.billingAddress = Validates.validityOf(billingAddress);
    }


    public CreditCardType getCardType() {
        return cardType;
    }

    public String getCardCommonName() {
        return cardType.commonName();
    }

    public String getCardNumber() {
        return cardNumber.get();
    }
    
    public String getMaskedCardNumber(){
    	String numStr = cardNumber.get();
    	if (numStr != null && numStr.length() >= 4) {
    		return "XXXX-XXXX-XXXX-" + numStr.substring(numStr.length()-4, numStr.length());
    	}
    	return numStr;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate.get();
    }

    public String getFirstName() {
        return billingAddress.get().getFirstName();
    }

    public String getLastName() {
        return billingAddress.get().getLastName();
    }

    public String getEmail() {
        return billingAddress.get().getEmailAddress();
    }

    public String getStreet() {return billingAddress.get().getStreet();}

    public String getCity() {return billingAddress.get().getCity();}
}
