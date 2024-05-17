package com.kalinga.rest.webservices.restfulwebservices.user;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDate;

@Entity(name="userdetails")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Convert(converter = AttributeEncryptor.class)
    private String name;

    @Column(name = "hobby", columnDefinition = "TEXT")
    private String hobby;

    public void setHobby(String hobby) throws Exception{
        this.hobby = EncryptDecryptService.encryptMessage(hobby);
    }

    public String getHobby() throws Exception{
        return EncryptDecryptService.decryptMessage(hobby);
    }

    private String country;

    public void setCountry(String country) throws Exception{
        this.country = AesEncodeUtil.encrypt2Str(country,AesEncodeUtil.PRIVATE_KEY);
    }

    public String getCountry() throws Exception {
        return AesEncodeUtil.decrypt2Str(country,AesEncodeUtil.PRIVATE_KEY);
    }

    private static final String symkey = "secret-key-12345";

    @Column(name = "endata", columnDefinition = "bytea")
    @ColumnTransformer(
            read = "PGP_SYM_DECRYPT(endata,'"+symkey+"')",
            write = "PGP_SYM_ENCRYPT (?, '"+symkey+"')"
    )
    private String mydata;

//    private static final String PUBLIC_KEY;
//    private static final String PRIVATE_KEY;
//
//    static {
//        try {
//            PUBLIC_KEY = KeyLoader.loadPublicKey();
//            PRIVATE_KEY = KeyLoader.loadPrivateKey();
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to load keys", e);
//        }
//    }
//
//    private static final String readEx="pgp_pub_decrypt(asymdata, dearmor('" + PRIVATE_KEY + "'),'kalinga')";
//    private static final String writeEx= "pgp_pub_encrypt(?, dearmor('"+PUBLIC_KEY+"'))";
//
//    public static void main(String[] args) {
//        System.out.println(readEx);
//        System.out.println(writeEx);
//    }

//    String kk = KeyReader.getReadEx();

//    @Autowired
//    MyBean myBean;
//
//    static final String key = myBean.getKey();

    @Column(name="asymdata", columnDefinition = "bytea")
    @ColumnTransformer(
//            read = readEx,
//            write = writeEx
            read = "pgp_pub_decrypt(asymdata, dearmor('-----BEGIN PGP PRIVATE KEY BLOCK-----\n" + "\n" + "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\n" + "hjWUEKCutOWd\n" + "=IA1M\n" + "-----END PGP PRIVATE KEY BLOCK-----'),'kalinga')",
            write = "pgp_pub_encrypt(?, dearmor('-----BEGIN PGP PUBLIC KEY BLOCK-----\n" + "\n" + "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\n" + "=GkAY\n" + "-----END PGP PUBLIC KEY BLOCK-----'))"
    )
    private String asymdata;

    private LocalDate dob;

    public User() {
    }

    public User(Integer id, String name, String country, String mydata, String asymdata, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.country=country;
        this.mydata = mydata;
        this.asymdata = asymdata;
        this.dob = dob;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMydata() {
        return mydata;
    }

    public void setMydata(String mydata) {
        this.mydata = mydata;
    }

    public String getAsymdata() {
        return asymdata;
    }

    public void setAsymdata(String asymdata) {
        this.asymdata = asymdata;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hobby='" + hobby + '\'' +
                ", country='" + country + '\'' +
                ", mydata='" + mydata + '\'' +
                ", asymdata='" + asymdata + '\'' +
                ", dob=" + dob +
                '}';
    }
}
