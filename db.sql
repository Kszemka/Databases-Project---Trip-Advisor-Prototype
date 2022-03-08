CREATE SCHEMA project;
SET search_path to project;

DROP TABLE keypoints, trail, valmas, trail_has_keypoints, trip_list, plan_trip CASCADE;
DROP TRIGGER trip_update ON plan_trip;
DROP TRIGGER trip_delete ON trip_list;

CREATE TABLE keypoints(
id SERIAL PRIMARY KEY,
name char(32) NOT NULL,
height int,
shelter bool,
flash bool
);

INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Zakopane', 				750, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Trzydniowiański Wierch', 	1765, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Grześ', 					1653, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Rakoń', 					1879, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Wołowiec', 				2064, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Ornak', 					1854, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Zadni Ornak', 			1867, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Ciemniak', 				2006, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Małołaczniak', 			2104, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Kondracka Kopa', 			2005, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Giewont', 				1894, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Sarnia Skała', 			1377, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Kasprowy Wierch', 		1867, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Polana Chochołowska',		1150, 	true, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Kończysty Wierch',		2002, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Jarząbczy Wierch', 		2137, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Smocza Jama',				1120, 	false, 	true);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Jaskinia Mylna', 			1098, 	false, 	true);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Jaskinia Mroźna', 		1112, 	false, 	true);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Hala Ornak', 				1100, 	true, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Polana Strążyska',		1042, 	true, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Siklawica', 				1117, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Polana Kalatówki',		1250, 	true, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Przełęcz Liliowe',		1947, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Świnica', 				2301, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Przełęcz Zawrat', 		2159, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Kozi Wierch', 			2291, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Przełęcz Krzyżne',		2112, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Kiry',					755, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Kuźnice',					760, 	false, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Murowaniec',				1500, 	true, 	false);
INSERT INTO keypoints (name, height, shelter, flash) VALUES ('Hala Kondratowa',			1333, 	true, 	false);

CREATE VIEW shelters AS SELECT * FROM keypoints WHERE shelter=true;
CREATE VIEW start_points AS SELECT * FROM keypoints WHERE name='Zakopane' OR name='Kiry' OR name='Kuźnice';
CREATE VIEW caves as SELECT * FROM keypoints WHERE flash=true;
CREATE VIEW cols as SELECT * FROM keypoints WHERE name LIKE 'Przełęcz%';
CREATE VIEW peaks AS SELECT * FROM keypoints WHERE id NOT IN (SELECT id FROM shelters) AND id NOT IN (SELECT id FROM caves) AND id NOT IN (SELECT id FROM cols);  

CREATE TABLE valmas(
id SERIAL PRIMARY KEY,
name CHAR(32),
description CHAR(255)
);

INSERT INTO valmas (name, description) VALUES ('Dolina Strążyska', 'Dolina w polskich Tatrach Zachodnich, położona pomiędzy Doliną ku Dziurze a Doliną za Bramką. Właściwa nazwa doliny pochodzi od słowa strąga, w gwarze podhalańskiej oznaczającego rodzaj zagrody przeznaczonej do dojenia owiec.');
INSERT INTO valmas (name, description) VALUES ('Dolina Chochołowska', 'Największa i najdłuższa doliną w polskich Tatrach. Położona najdalej na zachód w obrębie Tatrzańskiego Parku Narodowego, ma szalone 10 km długości. Dalej już jest tylko Słowacja. Dolinę możemy szczególnie polecić na rodzinne spacery.');
INSERT INTO valmas (name, description) VALUES ('Dolina Starobociańska', 'Największe odgałęzienie doliny Chochołowskiej. Dolina jest pochodzenia glacjalnego, wykształciła się wskutek działalności lodowców, czego dowodem jest jej U kształtny przekrój. Nazwa doliny pochodzi od „starej roboty”.');
INSERT INTO valmas (name, description) VALUES ('Dolina Kościeliska', 'Jest położona w Tatrach Zachodnich, ma długość 9 km, dzięki czemu jest drugą co do wielkości doliną po polskiej stronie Tatr, zaraz po sąsiedniej Dolinie Chochołowskiej. Warto tu się wybrać, szczególnie, że przez wieki działo się w tej dolinie, oj działo.');
INSERT INTO valmas (name, description) VALUES ('Dolina Małej Łąki', 'Dolina Małej Łąki jest jedną z piękniejszych tatrzańskich dolin. Znajdziecie ją w Tatrach Zachodnich, kilka minut samochodem od centrum Zakopanego. Jest przykładem doliny walnej, czyli takiej, która biegnie od grani głównej gór, aż do ich podnóża. ');
INSERT INTO valmas (name, description) VALUES ('Dolina Kondratowa', 'Malownicza dolina w Tatrach Zachodnich, stanowiąca zachodnie odgałęzienie Doliny Bystrej. Leży ona w centralnej części Tatr, od południowo-zachodniej strony ograniczają ją zbocza Giewontu i Kopa Kalacka.');
INSERT INTO valmas (name, description) VALUES ('Dolina Suchej Wody', 'Jedna z tatrzańskich dolin walnych po północnej stronie grani głównej. Przebiega przez nią granica pomiędzy Tatrami Wysokimi i Zachodnimi.');
INSERT INTO valmas (name, description) VALUES ('Czerwone Wierchy', 'Czyli czterej muszkieterowie Tatr Zachodnich, chętnie goszczą o każdej porze roku. Jednak to jesienią bywa tutaj najpiękniej. Wtenczas Wierchy porośnięte trawą, lekko zaokrąglone przybierają kolorowe barwy.');
INSERT INTO valmas (name, description) VALUES ('Orla Perć', 'Orla Perć uważana jest często za najtrudniejszy szlak turystyczny w Tatrach. Śmiało poprowadzony w pobliżu grani łączy dwie przełęcze: Zawrat i Krzyżne. W wielu miejscach został wyposażony w sztuczne ułatwienia: łańcuchy, klamry, a nawet drabiny.');
INSERT INTO valmas (name, description) VALUES ('Dolina Roztoki', 'Położona wśród majestatycznych szczytów Tatr Wysokich, Dolina Pięciu Stawów Polskich jest celem licznych wędrówek. Trzeba jednak pamiętać, że wycieczka wymaga dobrej kondycji.');
INSERT INTO valmas (name, description) VALUES ('Dolina Rybiego Potoku', 'Ceprostrada.');
INSERT INTO valmas (name, description) VALUES ('Dolina Gąsienicowa', 'Dolia z gąsienicami. Uwaga!.');

CREATE TYPE color AS ENUM ('red', 'yellow', 'blue', 'green', 'black');

CREATE TABLE trail(
id SERIAL PRIMARY KEY,
t_color color,
level int,
A int,
timA TIME,
B int,
timB TIME,
valley_or_massif int,
FOREIGN KEY (valley_or_massif) REFERENCES valmas(id),
FOREIGN KEY (A) REFERENCES keypoints(id),
FOREIGN KEY (B) REFERENCES keypoints(id)
);

CREATE TABLE trail_has_keypoints(
id_trail int,
id_keypoint int,
timA TIME,
timB TIME,
PRIMARY KEY (id_trail, id_keypoint)
);

INSERT INTO trail (t_color, level, A, timA, B, timB, valley_or_massif) VALUES ('red', 1, 1, '03:30:00', 11, '02:30:00', 1);
INSERT INTO trail (t_color, level, A, timA, B, timB, valley_or_massif) VALUES ('green', 1, 1, '04:30:00', 5, '04:00:00', 2);
INSERT INTO trail (t_color, level, A, timA, B, timB, valley_or_massif) VALUES ('green', 1, 29, '05:15:00', 7, '04:30:00', 4);
INSERT INTO trail (t_color, level, A, timA, B, timB, valley_or_massif) VALUES ('yellow', 1, 1, '04:00:00', 10, '03:30:00', 5);
INSERT INTO trail (t_color, level, A, timA, B, timB, valley_or_massif) VALUES ('blue', 1, 30, '02:30:00', 10, '02:00:00', 6);
INSERT INTO trail (t_color, level, A, timA, B, timB, valley_or_massif) VALUES ('black', 1, 1, '02:1:005', 31, '02:00:00', 7);
INSERT INTO trail (t_color, level, A, timA, B, timB, valley_or_massif) VALUES ('red', 1, 8, '01:40:00', 10, '01:40:00', 8);
INSERT INTO trail (t_color, level, A, timA, B, timB, valley_or_massif) VALUES ('red', 1, 13, '09:00:00', 28, '09:00:00', 9);
INSERT INTO trail (t_color, level, A, timA, B, timB, valley_or_massif) VALUES ('blue', 1, 3, '01:50:00', 5, '01:30:00', 3);
INSERT INTO trail (t_color, level, A, timA, B, timB, valley_or_massif) VALUES ('yellow', 1, 1, '00:55:00', 22, '00:45:00', 1);
INSERT INTO trail (t_color, level, A, timA, B, timB, valley_or_massif) VALUES ('blue', 1, 30, '4:30:00', 26, '03:30:00', 12);
INSERT INTO trail (t_color, level, A, timA, B, timB, valley_or_massif) VALUES ('green', 1, 30, '03:15:00', 13, '02:30:00', 11);

INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (9, 4, '01:00:00', '00:40:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (2, 14, '01:30:00', '02:10:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (1, 21, '00:40:00', '00:35:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (10, 21, '00:40:00', '00:15:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (3, 17, '02:00:00', '03:15:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (3, 18, '02:00:00', '03:15:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (3, 19, '00:45:00', '04:30:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (3, 20, '01:30:00', '03:45:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (3, 6, '03:30:00', '1:30:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (4, 11, '01:50:00', '00:50:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (5, 23, '00:35:00', '03:30:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (5, 32, '01:10:00', '02:20:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (11, 31, '03:00:00', '01:30:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (8, 26, '03:00:00', '05:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (8, 24, '00:30:00', '06:30:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (8, 25, '02:15:00', '06:45:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (8, 27, '06:00:00', '03:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (1, 1, '00:00:00', '01:30:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (2, 1, '00:00:00', '04:30:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (3, 29, '00:00:00', '05:15:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (4, 1, '00:00:00', '04:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (5, 30, '00:00:00', '02:30:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (6, 1, '00:00:00', '02:1:005');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (7, 8, '00:00:00', '01:40:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (8, 13, '00:00:00', '09:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (9, 3, '00:00:00', '01:50:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (10, 1, '00:00:00', '00:55:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (11, 30, '00:00:00', '4:30:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (12, 30, '00:00:00', '03:15:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (1, 11, '02:34:00', '00:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (2, 5, '04:00:00', '00:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (3, 7, '04:30:00', '00:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (4, 10, '03:30:00', '00:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (5, 10, '02:00:00', '00:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (6, 31, '02:00:00', '00:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (7, 10, '01:40:00', '00:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (8, 28, '09:00:00', '00:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (9, 5, '01:30:00', '00:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (10, 22, '00:45:00', '00:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (11, 26, '03:30:00', '00:00:00');
INSERT INTO trail_has_keypoints (id_trail, id_keypoint, timA, timB) VALUES (12, 13, '02:30:00', '00:00:00');

CREATE TABLE trip_list(
id SERIAL PRIMARY KEY,
name char(100),
duration TIME
);

CREATE TABLE plan_trip(
id SERIAL PRIMARY KEY,
trip_no int NOT NULL,	
start int NOT NULL,
finish int NOT NULL,
trail int,
duration TIME,
FOREIGN KEY (trip_no) REFERENCES trip_list(id)
);

CREATE OR REPLACE FUNCTION add_point() RETURNS TRIGGER AS $$
	
	DECLARE 
	REC INT;
	TIM TIME;
	DUR TIME;
	MINDUR TIME;
	STIME TIME;
	FTIME TIME;
	TRAIL INT;

    BEGIN
		
		IF((SELECT COUNT(*) FROM(SELECT * from plan_trip WHERE trip_no = NEW.trip_no) as C)::INTEGER > 0 )  THEN
			NEW.start := (SELECT finish from plan_trip WHERE id = (SELECT MAX(id) FROM plan_trip WHERE trip_no = NEW.trip_no));
		END IF;
		
		IF ((SELECT COUNT(*) FROM(SELECT * from plan_trip WHERE trip_no = NEW.trip_no) as C) > 5) THEN
			RAISE EXCEPTION 'Za długa trasa!';
			RETURN NULL;
		
		ELSE
			MINDUR := '24:00:00';
			RAISE NOTICE 'MINDUR %.', MINDUR;
			IF NOT EXISTS (SELECT start.id_trail FROM (
					(SELECT id_trail FROM trail_has_keypoints WHERE id_keypoint = NEW.start) as start INNER JOIN 
					(SELECT id_trail FROM trail_has_keypoints WHERE id_keypoint = NEW.finish) as finish ON start.id_trail=finish.id_trail)) THEN 
					RAISE EXCEPTION 'Punkty nie są połączone';
					RETURN NULL;
			ELSE		 
				FOR REC in (SELECT start.id_trail FROM (
						(SELECT id_trail FROM trail_has_keypoints WHERE id_keypoint = NEW.start) as start INNER JOIN 
						(SELECT id_trail FROM trail_has_keypoints WHERE id_keypoint = NEW.finish) as finish ON start.id_trail=finish.id_trail))
				LOOP		
					STIME := (SELECT timA FROM trail_has_keypoints WHERE id_keypoint = NEW.start AND id_trail = REC)::INTERVAL;
					FTIME := (SELECT timA FROM trail_has_keypoints WHERE id_keypoint = NEW.finish AND id_trail = REC)::INTERVAL;
					RAISE NOTICE 'STIME %.', STIME;
					RAISE NOTICE 'FTIME %.', FTIME;
					IF (FTIME>STIME) THEN
						STIME := (SELECT timB FROM trail_has_keypoints WHERE id_keypoint = NEW.start AND id_trail = REC)::INTERVAL;
						FTIME := (SELECT timB FROM trail_has_keypoints WHERE id_keypoint = NEW.finish AND id_trail = REC)::INTERVAL;	
					END IF;
					DUR := STIME::INTERVAL - FTIME::INTERVAL;
					RAISE NOTICE 'DUR %.', DUR;
					IF(DUR::INTERVAL<MINDUR::INTERVAL) THEN
						MINDUR := DUR;
						RAISE NOTICE 'MINDUR %.', MINDUR;
						TRAIL := REC;
						RAISE NOTICE 'TRAIL %.', TRAIL;
					END IF;	
				END LOOP;	
				TIM := (SELECT duration FROM trip_list WHERE id = NEW.trip_no)::INTERVAL + MINDUR::INTERVAL;
				RAISE NOTICE 'TIM %.', TIM;
				NEW.trail := TRAIL;
				NEW.duration := DUR;
				UPDATE trip_list SET duration = TIM WHERE id = NEW.trip_no;
				RETURN NEW;	
			END IF;	
		END IF;	
    END;
$$ LANGUAGE 'plpgsql';  

CREATE TRIGGER trip_update BEFORE INSERT ON plan_trip FOR EACH ROW
EXECUTE PROCEDURE add_point();



CREATE OR REPLACE FUNCTION delete_points() RETURNS TRIGGER AS $$
	BEGIN
		ALTER TABLE plan_trip DISABLE TRIGGER lastpoint_delete;
	    DELETE FROM plan_trip WHERE trip_no IN (SELECT id FROM trip_list WHERE name = OLD.name);
		ALTER TABLE plan_trip ENABLE TRIGGER lastpoint_delete;
        RETURN OLD;
    END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER trip_delete BEFORE DELETE ON trip_list FOR EACH ROW
EXECUTE PROCEDURE delete_points();



CREATE OR REPLACE FUNCTION check_name() RETURNS TRIGGER AS $$

	BEGIN
		IF EXISTS(SELECT * FROM trip_list WHERE name = NEW.name) THEN
			RAISE EXCEPTION 'Taka wycieczka już istnieje';
	    END IF;
        RETURN NEW;
    END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER check_name BEFORE INSERT ON trip_list FOR EACH ROW
EXECUTE PROCEDURE check_name();


CREATE OR REPLACE FUNCTION delete_point() RETURNS TRIGGER AS $$

	BEGIN
	    UPDATE trip_list SET duration = (duration-OLD.duration) WHERE id = OLD.trip_no; 
		RETURN OLD;
    END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER lastpoint_delete BEFORE DELETE ON plan_trip FOR EACH ROW
EXECUTE PROCEDURE delete_point();
