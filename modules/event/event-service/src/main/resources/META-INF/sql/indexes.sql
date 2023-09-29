create index IX_5E26FFF5 on Event_Event (groupId, status);
create index IX_3D53F2E1 on Event_Event (status);
create index IX_487B82F on Event_Event (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_6FAE7E71 on Event_Event (uuid_[$COLUMN_LENGTH:75$], groupId);