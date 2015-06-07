package edu.neu.angelhack.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the item database table.
 * 
 */
@Entity
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int itemId;

	private String category;

	private BigDecimal itemAmount;

	//bi-directional many-to-one association to Tran
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TranId")
	private Tran tran;

	public Item() {
	}

	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getItemAmount() {
		return this.itemAmount;
	}

	public void setItemAmount(BigDecimal itemAmount) {
		this.itemAmount = itemAmount;
	}

	public Tran getTran() {
		return this.tran;
	}

	public void setTran(Tran tran) {
		this.tran = tran;
	}

}