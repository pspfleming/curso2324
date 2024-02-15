package unidad5;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class VerificarFirma {

	public static void main(String[] args) throws Exception {
		KeyStore ks = KeyStore.getInstance("pkcs12");
		char [] password = "practicas".toCharArray();
		ks.load(new FileInputStream("C:\\cygwin64\\home\\Julio\\certs\\keystore.p12"), password);
		PublicKey pubKey = (PublicKey) ks.getCertificate("julio").getPublicKey();
		
		try (
				BufferedInputStream inF = new BufferedInputStream(
						VerificarFirma.class.getResourceAsStream("/OpenSSL.pdf"));
				BufferedInputStream inS = new BufferedInputStream(
						new FileInputStream(System.getProperty("user.home") + "/Desktop/OpenSSL.pdf.sign"))
				) {
			Signature sign = Signature.getInstance("SHA512withRSA");
			sign.initVerify(pubKey);
			byte [] buffer = new byte[1024];
			int n;
			while ((n = inF.read(buffer)) > 0)
				sign.update(buffer, 0, n);
			System.out.println(sign.verify(inS.readAllBytes()));
		}
	}

}
