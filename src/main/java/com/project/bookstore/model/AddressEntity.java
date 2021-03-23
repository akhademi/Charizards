package com.project.bookstore.model;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS", schema = "JRV77878", catalog = "")
public class AddressEntity {
  private String addressId;
  private Integer streetNo;
  private String streetName;
  private String city;
  private String province;
  private String country;
  private String zip;
  private String phone;

  @Id
  @Column(name = "ADDRESS_ID")
  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  @Basic
  @Column(name = "STREET_NO")
  public Integer getStreetNo() {
    return streetNo;
  }

  public void setStreetNo(Integer streetNo) {
    this.streetNo = streetNo;
  }

  @Basic
  @Column(name = "STREET_NAME")
  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  @Basic
  @Column(name = "CITY")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Basic
  @Column(name = "PROVINCE")
  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  @Basic
  @Column(name = "COUNTRY")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Basic
  @Column(name = "ZIP")
  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  @Basic
  @Column(name = "PHONE")
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

}
