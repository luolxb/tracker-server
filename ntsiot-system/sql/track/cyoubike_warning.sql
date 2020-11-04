create table warning
(
    id              INT(10)     not null
        primary key,
    warning         TEXT(65535) null,
    Bitcoin_Address TEXT(65535) null,
    Email           TEXT(65535) null
);

INSERT INTO cyoubike.warning (id, warning, Bitcoin_Address, Email) VALUES (1, 'To recover your lost Database and avoid leaking it: Send us 0.045 Bitcoin (BTC) to our Bitcoin address 1Mt5beKhEbs12YHWDBWD4hZcCBpFfVMpoi and contact us by Email with your Server IP or Domain name and a Proof of Payment. If you are unsure if we have your data, contact us and we will send you a proof. Your Database is downloaded and backed up on our servers. Backups that we have right now: a319yatai, base-admin, chain_test, cyoubike, demo, deposit_certificate, dtumonitor, dtumonitor2, eladmin, nts-flow, ntscom, socket-data. If we dont receive your payment in the next 10 Days, we will make your database public or use them otherwise.', '1Mt5beKhEbs12YHWDBWD4hZcCBpFfVMpoi', 'mysqldumps@protonmail.com');