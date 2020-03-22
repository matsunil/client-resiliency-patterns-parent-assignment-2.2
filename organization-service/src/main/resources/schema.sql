DROP TABLE IF EXISTS organizations;

CREATE TABLE organizations (
  organization_id        VARCHAR(100) PRIMARY KEY NOT NULL,
  name                   TEXT NOT NULL,
  contact_name           TEXT NOT NULL,
  contact_email          TEXT NOT NULL,
  contact_phone          TEXT   NOT NULL);


INSERT INTO organizations (organization_id, name, contact_name, contact_email, contact_phone)
VALUES ('4d7b9c40-5c8f-11ea-bc55-0242ac130003', 'HR-Managing', 'Doug Johnson','doug.johnson@hrmanaging.com', '781-587-3456');

INSERT INTO organizations (organization_id, name, contact_name, contact_email, contact_phone)
VALUES ('d1375b3c-6171-11ea-bc55-0242ac130003', 'customer-crm-co', 'Mark Balster', 'mark.balster@custcrmco.com', '823-555-1212');
