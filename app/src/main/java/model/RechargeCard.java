package model;

public class RechargeCard {

    int id;
    String providerName;
    String cardNumber;

    public RechargeCard(int id, String providerName, String cardNumber) {
        this.id = id;
        this.providerName = providerName;
        this.cardNumber = cardNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
