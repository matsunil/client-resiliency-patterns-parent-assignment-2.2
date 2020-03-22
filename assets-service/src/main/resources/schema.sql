DROP TABLE IF EXISTS assets;

CREATE TABLE assets (
  asset_id        VARCHAR(100) PRIMARY KEY NOT NULL,
  organization_id   TEXT NOT NULL,
  asset_type      TEXT NOT NULL,
  asset_name      TEXT NOT NULL,
  comment           VARCHAR(100));


INSERT INTO assets (asset_id,  organization_id, asset_name, asset_type, comment)
VALUES ('e741a400-6171-11ea-bc55-0242ac130003', '4d7b9c40-5c8f-11ea-bc55-0242ac130003', 'computers','operating', 'valuable asset');
INSERT INTO assets (asset_id,  organization_id, asset_name, asset_type, comment)
VALUES ('3046b0ce-771d-4a3d-937f-6cde345659c4', '4d7b9c40-5c8f-11ea-bc55-0242ac130003', 'furniture','tangible', 'replace partially');
INSERT INTO assets (asset_id,  organization_id, asset_name, asset_type, comment)
VALUES ('fa230ac8-6171-11ea-bc55-0242ac130003', 'd1375b3c-6171-11ea-bc55-0242ac130003', 'power cord','operating', 'replace fully');
INSERT INTO assets (asset_id,  organization_id, asset_name, asset_type, comment)
VALUES ('02f52f5a-6172-11ea-bc55-0242ac130003', 'd1375b3c-6171-11ea-bc55-0242ac130003', 'chair','tangible', 'replace partially');
