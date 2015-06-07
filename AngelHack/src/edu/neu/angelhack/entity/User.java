package edu.neu.angelhack.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String userName;

	private String passWord;

	//bi-directional many-to-one association to Tran
	@OneToMany(mappedBy="user")
	private List<Tran> trans;

	public User() {
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return this.passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public List<Tran> getTrans() {
		return this.trans;
	}

	public void setTrans(List<Tran> trans) {
		this.trans = trans;
	}

	public Tran addTran(Tran tran) {
		getTrans().add(tran);
		tran.setUser(this);

		return tran;
	}

	public Tran removeTran(Tran tran) {
		getTrans().remove(tran);
		tran.setUser(null);

		return tran;
	}

}