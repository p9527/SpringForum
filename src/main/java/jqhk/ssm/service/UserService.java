package jqhk.ssm.service;


import jqhk.ssm.mapper.UserMapper;
import jqhk.ssm.model.UserModel;
import jqhk.ssm.model.UserRole;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {
	private UserMapper mapper;
	static String salt = "jqhkhahahahaahah";

	public UserService(UserMapper userMapper) {
		this.mapper = userMapper;
	}

	public UserModel add(String username, String password) {
		password = Digest.md5(password + salt);
		UserModel m = new UserModel();
		m.setUserName(username);
		m.setPassword(password);
		m.setRole(UserRole.normal);
		Long time = System.currentTimeMillis() / 1000L;
		m.setCreatedTime(time);
		m.setAvatar("avatar.gif");

		mapper.insertUser(m);
		return m;
	}

	public void update(String username, String password) {
		password = Digest.md5(password + salt);
		UserModel m = new UserModel();
		m.setUserName(username);
		m.setPassword(password);
		mapper.updateUser(m);
	}

	public void deleteById(Integer id) {
		mapper.deleteUser(id);
	}

	public UserModel findById(Integer id) {
		return mapper.selectUser(id);
	}

	public UserModel findByUsername(String username) {
		return mapper.selectOnerByUsername(username);
	}

	public boolean validateLogin(String username, String password) {
		password = Digest.md5(password + salt);
		UserModel user = mapper.selectOnerByUsername(username);
		return user != null && user.getPassword().equals(password);
	}

	public boolean validRegister(String username, String password) {
		UserModel user = mapper.selectOnerByUsername(username);
		int usernameLength = username.length();
		int passwordLength = password.length();

		if (usernameLength < 3 || usernameLength > 10) {
			return false;
		}

		if (passwordLength < 3) {
			return false;
		}

		for (int i = 0; i < username.length(); i++) {
			char e = username.charAt(i);
			if (!Character.isAlphabetic(e)) {
				return false;
			}
		}

		if (user != null) {
			return false;
		}

		return true;
	}

	public UserModel guest() {
		UserModel user = new UserModel();
		user.setRole(UserRole.guest);
		user.setId(-1);
		user.setUserName("游客");
		user.setPassword("游客");
		user.setNote("建议注册登录一个账户");
		user.setSource(-1);
		user.setAvatar("avatar.gif");
		return user;
	}

	public UserModel currentUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer id = (Integer) session.getAttribute("user_id");
		if (id == null) {
			return this.guest();
		}
		UserModel user = mapper.selectUser(id);
		if (user == null) {
			user = this.guest();
		}
		return user;
	}

	public List<UserModel> all() {
		return mapper.selectAllUser();
	}

	public List<UserModel> sourceOrderList(Integer count) {
		return mapper.sourceOrderList(count);
	}

	public List<UserModel> sourceTop100() {
		return mapper.selectSourceTop100();
	}

	public void sourceAdd(String userName) {
		this.mapper.sourceAdd(userName, 20);
	}

	public void updateSetting(String username, String note) {
		UserModel u = this.mapper.selectOnerByUsername(username);
		u.setNote(note);
		this.mapper.updateSetting(u);
	}

	public void changeAvatar(String avatar, int userId) {
		this.mapper.changeAvatar(avatar, userId);
	}
}

class Digest {
	public static String hexFromBytes(byte[] array) {
		String hex = new BigInteger(1, array).toString(16);
		// 001FF
		int zeroLength = array.length * 2 - hex.length();
		for (int i = 0; i < zeroLength; i++) {
			hex = "0" + hex;
		}
		return hex;
	}

	public static String md5(String origin) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			md.update(origin.getBytes());
			byte[] result = md.digest();
			String hex = hexFromBytes(result);
			return hex;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(String.format("error: %s", e));
		}
	}
}
