package edu.neu.angelhack.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the tran database table.
 * 
 */
@Entity
@NamedQuery(name="Tran.findAll", query="SELECT t FROM Tran t")
public class Tran implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int tranId;

	private BigDecimal tranAmount;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="tran")
	private List<Item> items;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="UserName")
	private User user;

	public Tran() {
	}

	public int getTranId() {
		return this.tranId;
	}

	public void setTranId(int tranId) {
		this.tranId = tranId;
	}

	public BigDecimal getTranAmount() {
		return this.tranAmount;
	}

	public void setTranAmount(BigDecimal tranAmount) {
		this.tranAmount = tranAmount;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Item addItem(Item item) {
		getItems().add(item);
		item.setTran(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setTran(null);

		return item;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}