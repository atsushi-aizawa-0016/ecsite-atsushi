package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurtyConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * このメソッドをオーバーライドすることで、
	 * 特定のリクエストに対して「セキュリティ設定」を
	 * 無視する設定など全体にかかわる設定ができる.
	 * 具体的には静的リソースに対してセキュリティの設定を無効にする。
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers( "/css/**"
						, "/img/**"
						, "/js/**"
						, "/fonts/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.authorizeRequests()
			.antMatchers("/user/**" //ユーザーがパスをダイレクトに書いても直接観れる部分
						,"/item/**"//contoroller内の最初のパス以降が全てユーザーが直接アクセスできるなら/**で全ての意味になる
						,"/toLogin"
						).permitAll()
			.anyRequest().authenticated();
		
		http.formLogin()
			.loginPage("/user") //コントローラーの
			.loginProcessingUrl("/toLogin")
			.failureUrl("/toLogin?error=false")
			.defaultSuccessUrl("/item/showItemList",false)
			.usernameParameter("email")
			.passwordParameter("password"); 
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
			.logoutSuccessUrl("/")
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true);
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	/**
	  * <pre>
	  * bcryptアルゴリズムでハッシュ化する実装を返します.
	  * これを指定することでパスワード暗号化やマッチ確認する際に
	  * @Autowired
	  * private PasswordEncoder passwordEncoder;
	  * と記載するとDIされるようになります。
	  * </pre>
	  * @return bcryptアルゴリズムで暗号化する実装オブジェクト
	  */
	 @Bean
	 public PasswordEncoder passwordEncoder() {
	     return new BCryptPasswordEncoder();
	 }
	 
		/**
		 * 複合化に使用するメソッド
		 * 
		 * @param secret
		 * @param salt
		 * @param plainText
		 * @return 複合化したテキスト
		 */
		public String encryptText(String secret, String salt, String plainText) {
			TextEncryptor encrypt = Encryptors.text(secret, salt);
			return encrypt.encrypt(plainText);
		}
		
		
		/**
		 * 暗号化に使用するメソッド
		 * 
		 * @return 暗号化したテキスト
		 */
		public String decryptText(String secret, String salt, String cipherText) {
			TextEncryptor decrypt = Encryptors.text(secret, salt);
			return decrypt.decrypt(cipherText);
		}
}
