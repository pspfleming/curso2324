package unidad5;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CifradoFicheros {

	public static void main(String[] args) throws Exception {
		KeyStore ks = KeyStore.getInstance("pkcs12");
		char [] password = "practicas".toCharArray();
		ks.load(new FileInputStream("C:/cygwin64/home/Julio/certs/keystore.p12"), password);
		PrivateKey privKey = (PrivateKey) ks.getKey("julio", "practicas".toCharArray());
		Certificate miCert = ks.getCertificate("julio");
		PublicKey pubKey = (PublicKey) ks.getCertificate("julio").getPublicKey();
		cifrar(System.getProperty("user.home") + "\\Documents\\OpenSSL.pdf", pubKey);
	}
	
//	static public void cifrar(String path, Key key) {
//		Cipher cipher;
//		try {
//			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//			cipher.init(Cipher.ENCRYPT_MODE, key);
//			try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
//					CipherOutputStream out = new CipherOutputStream(
//							new FileOutputStream(new File(path + ".cifrado")), cipher)) {
////				byte[] bloque = new byte[cipher.getBlockSize()];
//				byte[] bloque = new byte[1024];
//				int n;
//				while ((n = in.read(bloque)) != -1)
//					out.write(bloque, 0, n);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
//			e.printStackTrace();
//		}
//	}
	
	static public void cifrar(String path, Key key) {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
					BufferedOutputStream out = new BufferedOutputStream(
							new FileOutputStream(new File(path + ".cifrado")))) {
				byte[] bloque = new byte[501];
				int n;
				while ((n = in.read(bloque)) != -1) {
						out.write(cipher.doFinal(bloque, 0, n));
				}
				
			} catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}
	}

}
