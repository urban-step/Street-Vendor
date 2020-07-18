package com.example.tbc.model;

import java.io.Serializable;

public class SurveyFormData implements Serializable {
    private String abled;
    private String govtScheme;
    private String adharCard;
    private String age;
    private String amount;
    private String bankAccount;
    private String branchName;
    private String bankName;
    private String ward_zone;
    private String typeOfBenefit;
    private String dateOfVending;
    private String familyEarning;
    private String file;
    private String homeNumber;
    private String locality;
    private String wardZone;
    private String state;
    private String city;
    private String pinCode;
    private String mobileNumber;
    private String vendorHomeNumber;
    private String vendorLocality;
    private String vendorWardZone;
    private String vendorState;
    private String vendorCity;
    private String vendorPinCode;

    public String getWard_zone() {
        return ward_zone;
    }

    public void setWard_zone(String ward_zone) {
        this.ward_zone = ward_zone;
    }

    public String getVendorwardzone() {
        return vendorwardzone;
    }

    public void setVendorwardzone(String vendorwardzone) {
        this.vendorwardzone = vendorwardzone;
    }

    public String getIsLicense() {
        return isLicense;
    }

    public void setIsLicense(String isLicense) {
        this.isLicense = isLicense;
    }

    private String vendorwardzone;
    private String locationlatitude;
    private String locationlongitude;
    private String f55id;
    private String idProof;
    private String dailyIncome;
    private String takingLoans;
    private String location;
    private String natureOfVending;
    private String nameNominee;
    private String nomineeAge;
    private String adharCardNominee;
    private String mobileNumberNominee;
    private String paymentOption;
    private String registrationPayment;
    private String qualification;
    private String nomineeRelationship;
    private String surveyor;
    private String timing;
    private String typeCommodity;
    private String licenseNumber;
    private String vendorName;
    private String isLicense;
    private String nomineeOccupation;

    public String other_scheme_name ;
    public String six_pm ;
    public String three_pm ;
    public String twell_pm ;
    public String nine_am ;
    public String six_am ;
    public String other_commodity_option ;
    public String file_id ;
    public String file_callan ;
    public String years_vending ;
    public String file_stall ;
    public String reads_sec ;
    public String file_license ;
    public String callan_number ;
    public String other_qualification; ;
    public String scheme ;
    public String types_of_abled;


    public SurveyFormData() {
        String str = "";
        String str2 = "demo";
        this.abled = str ;
        this.govtScheme = str ;
        this.adharCard = str ;
        this.age = str ;
        this.amount = str ;
        this.bankAccount = str ;
        this.branchName = str ;
        this.bankName = str ;
        this.typeOfBenefit = str ;
        this.dateOfVending = str ;
        this.familyEarning = str ;
        this.file = str ;
        this.homeNumber = str ;
        this.locality = str ;
        this.wardZone = str ;
        this.state = str ;
        this.city = str ;
        this.pinCode = str ;
        this.mobileNumber = str ;
        this.vendorHomeNumber = str ;
        this.vendorLocality = str ;
        this.vendorWardZone = str ;
        this.vendorState = str ;
        this.vendorCity = str ;
        this.vendorPinCode = str ;
        this.locationlatitude = str ;
        this.locationlongitude = str ;
        this.f55id = str ;
        this.idProof = str ;
        this.dailyIncome = str ;
        this.takingLoans = str ;
        this.location = str ;
        this.natureOfVending = str ;
        this.nameNominee = str ;
        this.nomineeAge = str ;
        this.adharCardNominee = str ;
        this.mobileNumberNominee = str ;
        this.paymentOption = str ;
        this.registrationPayment = str ;
        this.qualification = str ;
        this.nomineeRelationship = str ;
        this.surveyor = str ;
        this.timing = str ;
        this.typeCommodity = str ;
        this.licenseNumber = str ;
        this.vendorName = str ;
        this.isLicense = str ;
        this.nomineeOccupation = str;
        this.other_scheme_name=str;
        this.six_pm=str2;
        this.three_pm=str2;
        this.twell_pm=str2;
        this.nine_am=str2;
        this.six_am=str2;
        this.other_commodity_option=str;
        this.file_id=str;
        this.file_callan=str;
        this.years_vending=str;
        this.file_stall=str;
        this.reads_sec=str;
        this.file_license=str;
        this.callan_number=str;
        this.other_qualification=str;
        this.scheme=str;
        this.types_of_abled =str;

    }



    public String getAbled() {
        return abled;
    }

    public void setAbled(String abled) {
        this.abled = abled;
    }

    public String getGovtScheme() {
        return govtScheme;
    }

    public void setGovtScheme(String govtScheme) {
        this.govtScheme = govtScheme;
    }

    public String getAdharCard() {
        return adharCard;
    }

    public void setAdharCard(String adharCard) {
        this.adharCard = adharCard;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTypeOfBenefit() {
        return typeOfBenefit;
    }

    public void setTypeOfBenefit(String typeOfBenefit) {
        this.typeOfBenefit = typeOfBenefit;
    }

    public String getDateOfVending() {
        return dateOfVending;
    }

    public void setDateOfVending(String dateOfVending) {
        this.dateOfVending = dateOfVending;
    }

    public String getFamilyEarning() {
        return familyEarning;
    }

    public void setFamilyEarning(String familyEarning) {
        this.familyEarning = familyEarning;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getWardZone() {
        return wardZone;
    }

    public void setWardZone(String wardZone) {
        this.wardZone = wardZone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getVendorHomeNumber() {
        return vendorHomeNumber;
    }

    public void setVendorHomeNumber(String vendorHomeNumber) {
        this.vendorHomeNumber = vendorHomeNumber;
    }

    public String getVendorLocality() {
        return vendorLocality;
    }

    public void setVendorLocality(String vendorLocality) {
        this.vendorLocality = vendorLocality;
    }

    public String getVendorWardZone() {
        return vendorWardZone;
    }

    public void setVendorWardZone(String vendorWardZone) {
        this.vendorWardZone = vendorWardZone;
    }

    public String getVendorState() {
        return vendorState;
    }

    public void setVendorState(String vendorState) {
        this.vendorState = vendorState;
    }

    public String getVendorCity() {
        return vendorCity;
    }

    public void setVendorCity(String vendorCity) {
        this.vendorCity = vendorCity;
    }

    public String getVendorPinCode() {
        return vendorPinCode;
    }

    public void setVendorPinCode(String vendorPinCode) {
        this.vendorPinCode = vendorPinCode;
    }

    public String getLocationlatitude() {
        return locationlatitude;
    }

    public void setLocationlatitude(String locationlatitude) {
        this.locationlatitude = locationlatitude;
    }

    public String getLocationlongitude() {
        return locationlongitude;
    }

    public void setLocationlongitude(String locationlongitude) {
        this.locationlongitude = locationlongitude;
    }

    public String getF55id() {
        return f55id;
    }

    public void setF55id(String f55id) {
        this.f55id = f55id;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(String dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    public String getTakingLoans() {
        return takingLoans;
    }

    public void setTakingLoans(String takingLoans) {
        this.takingLoans = takingLoans;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNatureOfVending() {
        return natureOfVending;
    }

    public void setNatureOfVending(String natureOfVending) {
        this.natureOfVending = natureOfVending;
    }

    public String getNameNominee() {
        return nameNominee;
    }

    public void setNameNominee(String licenseNominee) {
        this.nameNominee = licenseNominee;
    }

    public String getNomineeAge() {
        return nomineeAge;
    }

    public void setNomineeAge(String nomineeAge) {
        this.nomineeAge = nomineeAge;
    }

    public String getAdharCardNominee() {
        return adharCardNominee;
    }

    public void setAdharCardNominee(String adharCardNominee) {
        this.adharCardNominee = adharCardNominee;
    }

    public String getMobileNumberNominee() {
        return mobileNumberNominee;
    }

    public void setMobileNumberNominee(String mobileNumberNominee) {
        this.mobileNumberNominee = mobileNumberNominee;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getRegistrationPayment() {
        return registrationPayment;
    }

    public void setRegistrationPayment(String registrationPayment) {
        this.registrationPayment = registrationPayment;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getNomineeRelationship() {
        return nomineeRelationship;
    }

    public void setNomineeRelationship(String nomineeRelationship) {
        this.nomineeRelationship = nomineeRelationship;
    }

    public String getSurveyor() {
        return surveyor;
    }

    public void setSurveyor(String surveyor) {
        this.surveyor = surveyor;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getTypeCommodity() {
        return typeCommodity;
    }

    public void setTypeCommodity(String typeCommodity) {
        this.typeCommodity = typeCommodity;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getLicense() {
        return isLicense;
    }

    public void setLicense(String license) {
        isLicense = license;
    }


    public String getNomineeOccupation() {
        return nomineeOccupation;
    }

    public void setNomineeOccupation(String nomineeOccupation) {
        this.nomineeOccupation = nomineeOccupation;
    }

    public String getOther_scheme_name() {
        return other_scheme_name;
    }

    public void setOther_scheme_name(String other_scheme_name) {
        this.other_scheme_name = other_scheme_name;
    }

    public String getSix_pm() {
        return six_pm;
    }

    public void setSix_pm(String six_pm) {
        this.six_pm = six_pm;
    }

    public String getThree_pm() {
        return three_pm;
    }

    public void setThree_pm(String three_pm) {
        this.three_pm = three_pm;
    }

    public String getTwell_pm() {
        return twell_pm;
    }

    public void setTwell_pm(String twell_pm) {
        this.twell_pm = twell_pm;
    }

    public String getNine_am() {
        return nine_am;
    }

    public void setNine_am(String nine_am) {
        this.nine_am = nine_am;
    }

    public String getSix_am() {
        return six_am;
    }

    public void setSix_am(String six_am) {
        this.six_am = six_am;
    }

    public String getOther_commodity_option() {
        return other_commodity_option;
    }

    public void setOther_commodity_option(String other_commodity_option) {
        this.other_commodity_option = other_commodity_option;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getFile_callan() {
        return file_callan;
    }

    public void setFile_callan(String file_callan) {
        this.file_callan = file_callan;
    }

    public String getYears_vending() {
        return years_vending;
    }

    public void setYears_vending(String years_vending) {
        this.years_vending = years_vending;
    }

    public String getFile_stall() {
        return file_stall;
    }

    public void setFile_stall(String file_stall) {
        this.file_stall = file_stall;
    }

    public String getReads_sec() {
        return reads_sec;
    }

    public void setReads_sec(String reads_sec) {
        this.reads_sec = reads_sec;
    }

    public String getFile_license() {
        return file_license;
    }

    public void setFile_license(String file_license) {
        this.file_license = file_license;
    }

    public String getCallan_number() {
        return callan_number;
    }

    public void setCallan_number(String callan_number) {
        this.callan_number = callan_number;
    }

    public String getOther_qualification() {
        return other_qualification;
    }

    public void setOther_qualification(String other_qualification) {
        this.other_qualification = other_qualification;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getTypes_of_abled() {
        return types_of_abled;
    }

    public void setTypes_of_abled(String types_of_abled) {
        this.types_of_abled = types_of_abled;
    }
}
