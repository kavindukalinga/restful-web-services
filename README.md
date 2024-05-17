# Encryption

1.	Feasibility of similarity search on an encrypted table.

	
```sql
SELECT pgp_sym_decrypt(password::bytea, 'secret-key-12345') FROM users WHERE pgp_sym_decrypt(password::bytea, 'secret-key-12345') ILIKE '%12%';
```

```sql
SELECT pgp_pub_decrypt(asymdata, dearmor(${pk}), 'kalinga')  FROM userdetails WHERE pgp_pub_decrypt(asymdata, dearmor(${pk}), 'kalinga') ILIKE '%asym%';
```

```sql
INSERT INTO friend (name, age) VALUES (
    PGP_SYM_ENCRYPT('John','AES_KEY'),
    PGP_SYM_ENCRYPT('22', 'AES_KEY')
);

UPDATE friend SET 
(name, age) = (
    PGP_SYM_ENCRYPT('Jona', 'AES_KEY'),
    PGP_SYM_ENCRYPT('15', 'AES_KEY')
) WHERE id='1';
SELECT 
    PGP_SYM_DECRYPT(name::bytea, 'AES_KEY') as name,
    PGP_SYM_DECRYPT(age::bytea, 'AES_KEY') as age
FROM friend WHERE ( 
    LOWER(PGP_SYM_DECRYPT(name::bytea, 'AES_KEY')
    LIKE LOWER('%John%')
);


SELECT *, PGP_SYM_DECRYPT(endata, 'secret-key-12345') as decryptdata FROM public.userdetails
```


2. Extension of application-level support
	a. Hibernate support in Spring Boot (Hikari Connection Pooling), targeting seamless column fields - Developing a Spring Boot application in **_Java 11_** is expected. 
	- sym : done (java 17, Spring Boot 3.2.5, Maven)
	- asym : done (java 17, Spring Boot 3.2.5, Maven)

	b. Methods of injecting keys.
	- Store in Database server or postgres.conf
	- Read files: 
	@ColumnTransformer
	Error `java: element value must be a constant expression`

	- GNU Privacy Guard - GPG


3. Possibilities for supporting different data types in table columns for encrypted fields.
	- Postgres pgcrypto supports only bytea
	Or we have to use
	`PGP_SYM_ENCRYPT('Jona', 'AES_KEY')`
	`PGP_SYM_DECRYPT(name::bytea, 'AES_KEY') as name`

	`name::bytea` is a casting function, which can be translated to ‘CAST(‘name’ AS BYTEA)’.

	- Only text can be encrypted, no Integers



4. Explore the support in DBeaver for storing keys and reusing them.

	- Store in `Variables` and access using 
	```sql
	DECLARE
    private_key TEXT := ${pk};
    ```

	- Store as a `Properties-SQL Editor-Template`

	Here we have to use the template usinf `key` +ctrl+shift+space

### Example:
	
 ```sql
DO $$
DECLARE
    private_key TEXT := ${pk};
BEGIN
    DROP TABLE IF EXISTS decrypted_data; -- Drop the table if it already exists
    CREATE TEMP TABLE decrypted_data AS
    select
    	* ,
        pgp_pub_decrypt(asymdata, dearmor(private_key), 'kalinga') AS ddata
    FROM
        public.userdetails;
END $$;
SELECT * FROM decrypted_data;

```

5. Backup and restore operations on database dumps.
	- Works, Tested using PGAdmin4


6. Any limitations of using this approach, such as:
	- Performance overheads.
		- In Progress

	- Any impact on index operations.
		- In Progress


7. Optional: Explore query join support on encrypted fields.
	- Tested Relations like many-to-many : works
	- join queries: need to decrypt first


8. Optional: Conduct a load test targeting decryption queries.
	- In Progress


9. RSA Key Expiration
	- Backup b4 Expire (only option with RSA)


## Tested Encryption Approaches:

	- user.name :  @Convert(converter = AttributeEncryptor.class)	: AES (vary)	: secretkey Hard coded

	- user.hobby : EncryptDecryptService   :	"RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING"  4096 bits	: Read files to get keys

	- user.country : AesEncodeUtil : AES 128	: @Value("${text.privateKey}")

	- user.mydata -> (endata::bytea) : PGP_SYM_ENCRYPT 	: @ColumnTransformer	: secretkey Hard coded

	- user.asymdata : pgp_pub_encrypt	: @ColumnTransformer	: secretkey Hard coded

	- termaccess: rsa3072
		- ProcessBuilder("gpg", "--encrypt", "--recipient", recipient, "--armor");
		- ProcessBuilder("gpg", "--decrypt", "--batch", "--quiet", "--pinentry-mode", "loopback", "--passphrase","kalinga" );

		- mage.name : 
				- Encryption: @ColumnTransformer
				- Decryption: - ProcessBuilder("gpg", "--decrypt", "--batch", "--quiet", "--pinentry-mode", "loopback", "--passphrase","kalinga" );


