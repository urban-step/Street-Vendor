package com.example.tbc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorData {
    @SerializedName("abled")
    @Expose
    private String abled;
    @SerializedName("about_scheme")
    @Expose
    private String aboutScheme;
    @SerializedName("adhar_card")
    @Expose
    private String adharCard;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("bank_account")
    @Expose
    private String bankAccount;
    @SerializedName("benefit_taking")
    @Expose
    private String benefitTaking;
    @SerializedName("date_of_vending")
    @Expose
    private String dateOfVending;
    @SerializedName("family_earning")
    @Expose
    private String familyEarning;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("home_address")
    @Expose
    private String homeAddress;
    @SerializedName("id")
    @Expose

    /* renamed from: id */
    private String f55id;
    @SerializedName("id_proof")
    @Expose
    private String idProof;
    @SerializedName("income")
    @Expose
    private String income;
    @SerializedName("loans")
    @Expose
    private String loans;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("nature_of_vending")
    @Expose
    private String natureOfVending;
    @SerializedName("nominee_license")
    @Expose
    private String nomineeLicense;
    @SerializedName("occupation_nominee")
    @Expose
    private String occupationNominee;
    @SerializedName("payment_option")
    @Expose
    private String paymentOption;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("relationship_nominee")
    @Expose
    private String relationshipNominee;
    @SerializedName("surveyor")
    @Expose
    private String surveyor;
    @SerializedName("timing")
    @Expose
    private String timing;
    @SerializedName("type_commodity")
    @Expose
    private String typeCommodity;
    @SerializedName("url")
    @Expose
    private Object url;
    @SerializedName("vending_license")
    @Expose
    private String vendingLicense;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;

    public String getId() {
        return this.f55id;
    }

    public void setId(String id) {
        this.f55id = id;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public void setVendorName(String vendorName2) {
        this.vendorName = vendorName2;
    }

    public String getHomeAddress() {
        return this.homeAddress;
    }

    public void setHomeAddress(String homeAddress2) {
        this.homeAddress = homeAddress2;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age2) {
        this.age = age2;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location2) {
        this.location = location2;
    }

    public String getTypeCommodity() {
        return this.typeCommodity;
    }

    public void setTypeCommodity(String typeCommodity2) {
        this.typeCommodity = typeCommodity2;
    }

    public String getTiming() {
        return this.timing;
    }

    public void setTiming(String timing2) {
        this.timing = timing2;
    }

    public String getIncome() {
        return this.income;
    }

    public void setIncome(String income2) {
        this.income = income2;
    }

    public String getLoans() {
        return this.loans;
    }

    public void setLoans(String loans2) {
        this.loans = loans2;
    }

    public String getAbled() {
        return this.abled;
    }

    public void setAbled(String abled2) {
        this.abled = abled2;
    }

    public String getQualification() {
        return this.qualification;
    }

    public void setQualification(String qualification2) {
        this.qualification = qualification2;
    }

    public String getFamilyEarning() {
        return this.familyEarning;
    }

    public void setFamilyEarning(String familyEarning2) {
        this.familyEarning = familyEarning2;
    }

    public String getNomineeLicense() {
        return this.nomineeLicense;
    }

    public void setNomineeLicense(String nomineeLicense2) {
        this.nomineeLicense = nomineeLicense2;
    }

    public String getRelationshipNominee() {
        return this.relationshipNominee;
    }

    public void setRelationshipNominee(String relationshipNominee2) {
        this.relationshipNominee = relationshipNominee2;
    }

    public String getOccupationNominee() {
        return this.occupationNominee;
    }

    public void setOccupationNominee(String occupationNominee2) {
        this.occupationNominee = occupationNominee2;
    }

    public String getIdProof() {
        return this.idProof;
    }

    public void setIdProof(String idProof2) {
        this.idProof = idProof2;
    }

    public String getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(String bankAccount2) {
        this.bankAccount = bankAccount2;
    }

    public String getAboutScheme() {
        return this.aboutScheme;
    }

    public void setAboutScheme(String aboutScheme2) {
        this.aboutScheme = aboutScheme2;
    }

    public String getBenefitTaking() {
        return this.benefitTaking;
    }

    public void setBenefitTaking(String benefitTaking2) {
        this.benefitTaking = benefitTaking2;
    }

    public String getAdharCard() {
        return this.adharCard;
    }

    public void setAdharCard(String adharCard2) {
        this.adharCard = adharCard2;
    }

    public Object getUrl() {
        return this.url;
    }

    public void setUrl(Object url2) {
        this.url = url2;
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(String file2) {
        this.file = file2;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount2) {
        this.amount = amount2;
    }

    public String getSurveyor() {
        return this.surveyor;
    }

    public void setSurveyor(String surveyor2) {
        this.surveyor = surveyor2;
    }

    public String getVendingLicense() {
        return this.vendingLicense;
    }

    public void setVendingLicense(String vendingLicense2) {
        this.vendingLicense = vendingLicense2;
    }

    public String getDateOfVending() {
        return this.dateOfVending;
    }

    public void setDateOfVending(String dateOfVending2) {
        this.dateOfVending = dateOfVending2;
    }

    public String getNatureOfVending() {
        return this.natureOfVending;
    }

    public void setNatureOfVending(String natureOfVending2) {
        this.natureOfVending = natureOfVending2;
    }

    public String getPaymentOption() {
        return this.paymentOption;
    }

    public void setPaymentOption(String paymentOption2) {
        this.paymentOption = paymentOption2;
    }
}
