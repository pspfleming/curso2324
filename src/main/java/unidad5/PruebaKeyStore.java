package unidad5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Arrays;

public class PruebaKeyStore {

	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException {
		KeyStore ks = KeyStore.getInstance("pkcs12");
		char [] password = "practicas".toCharArray();
		ks.load(new FileInputStream("C:\\cygwin64\\home\\Julio\\certs\\keystore.p12"), password);
		
		System.out.println("PRIVADA");
		PrivateKey privKey = (PrivateKey) ks.getKey("julio", "practicas".toCharArray());
		System.out.println(privKey.getAlgorithm());
		System.out.println(privKey.getFormat());
		System.out.println(Arrays.toString(privKey.getEncoded()));
		System.out.println(privKey.getClass());
		
		System.out.println("\nCERTIFICADO");
		Certificate miCert = ks.getCertificate("julio");
		System.out.println(miCert.getType());
		System.out.println(Arrays.toString(miCert.getEncoded()));
		
		System.out.println("\nPÚBLICA");
		PublicKey pubKey = (PublicKey) miCert.getPublicKey();
		System.out.println(pubKey.getAlgorithm());
		System.out.println(pubKey.getFormat());
		System.out.println(Arrays.toString(pubKey.getEncoded()));
		System.out.println(pubKey.getClass());
	}
	
}
