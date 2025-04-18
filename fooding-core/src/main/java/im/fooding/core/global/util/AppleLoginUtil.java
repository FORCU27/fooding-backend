package im.fooding.core.global.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

public class AppleLoginUtil {

    /**
     * 애플로그인 ClientSecret 만드는 함수
     *
     * @param teamId
     * @param clientId
     * @param keyId
     * @param privateKeyContent
     * @return String
     * @throws Exception
     */
    public static String generateClientSecret(String teamId, String clientId, String keyId, String privateKeyContent) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyContent);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("EC");
        ECPrivateKey privateKey = (ECPrivateKey) kf.generatePrivate(keySpec);

        long now = System.currentTimeMillis();
        return JWT.create()
                .withIssuer(teamId)
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + 1000L * 60 * 60 * 24 * 180)) // 6개월
                .withAudience("https://appleid.apple.com")
                .withSubject(clientId)
                .withKeyId(keyId)
                .sign(Algorithm.ECDSA256(null, privateKey));
    }

    /**
     * idToken jwt 파싱해서 email 가져오는 함수
     *
     * @param idToken
     * @return String
     */
    public static String parseIdToken(String idToken) {
        return JWT.decode(idToken).getClaim("email").asString();
    }
}
