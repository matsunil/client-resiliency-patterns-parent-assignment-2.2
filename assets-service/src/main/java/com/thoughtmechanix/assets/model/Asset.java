package com.thoughtmechanix.assets.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "assets")
public class Asset{
  @Id
  @Column(name = "asset_id", nullable = false)
  private String assetId;

  @Column(name = "organization_id", nullable = false)
  private String organizationId;

  @Transient
  private String organizationName ="";

  @Transient
  private String contactName ="";

  @Transient
  private String contactPhone ="";

  @Transient
  private String contactEmail ="";

  @Column(name = "asset_name", nullable = false)
  private String assetName;

  @Column(name = "asset_type", nullable = false)
  private String assetType;

  @Column(name="comment")
  private String comment;


  public String getAssetId() {
    return assetId;
  }

  public void setAssetId(String assetId) {
    this.assetId = assetId;
  }

  public String getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(String organizationId) {
    this.organizationId = organizationId;
  }

  public String getAssetName() {
    return assetName;
  }

  public void setAssetName(String assetName) {
    this.assetName = assetName;
  }

  public String getAssetType() {
    return assetType;
  }

  public void setAssetType(String assetType) {
    this.assetType = assetType;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getOrganizationName() {
    return organizationName;
  }

  public void setOrganizationName(String organizationName) {
    this.organizationName = organizationName;
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public Asset withId(String id){
    this.setAssetId(id);
    return this;
  }

  public Asset withOrganizationId(String organizationId){
    this.setOrganizationId(organizationId);
    return this;
  }

  public Asset withAssetName(String assetName){
    this.setAssetName(assetName);
    return this;
  }

  public Asset withAssetType(String assetType){
    this.setAssetType(assetType);
    return this;
  }

  public Asset withComment(String comment){
    this.setComment(comment);
    return this;
  }

  public Asset withOrganizationName(String organizationName){
    this.setOrganizationName(organizationName);
    return this;
  }

  public Asset withContactName(String contactName){
    this.setContactName(contactName);
    return this;
  }

  public Asset withContactPhone(String contactPhone){
    this.setContactPhone(contactPhone);
    return this;
  }

  public Asset withContactEmail(String contactEmail){
    this.setContactEmail(contactEmail);
    return this;
  }




}
