insert into artikels(naam, aankoopprijs, verkoopprijs, soort, houdbaarheid)
values ('testFoodA', 2, 4, 'F', 7);
insert into artikels(naam, aankoopprijs, verkoopprijs, soort, garantie)
values ('testNonFoodA', 3, 6, 'NF', 7);
insert into kortingen(artikelId, vanafAantal, percentage)
VALUES ((select id from artikels where naam = 'testFoodA'), 1, 10);