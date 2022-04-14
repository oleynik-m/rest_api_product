package com.example.product.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
public class Product {




    private @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    private @Column(nullable = false,unique=true) String name;
    private String description;
    private Date createDate;
    private Date updateDate;
    @DecimalMin(value = "1.0")
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;


    @Transient
    List<String> formatStrings = Arrays.asList("yyyy-MM-dd","M/y", "M/d/y", "M-d-y");




    public Product(String name, String description, BigDecimal amount, Currency currency, Language language) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.language = language;
    }

    public Product () {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

     public String getCreateDate() {
        if (this.createDate!=null)
            return this.DateToString(this.createDate);
        else
            return null;
    }

      public void setCreateDate(String createDate) {
        if (createDate!=null)
            this.createDate = this.toDate(createDate);
        else
            return;
    }

    public String getUpdateDate() {
        if (this.updateDate!=null)
            return this.DateToString(this.updateDate);
        else
            return null;
    }

    public void setUpdateDate(String updateDate) {
        if (updateDate!=null)
            this.updateDate = this.toDate(updateDate);
        else
            return;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    private Date toDate(String dateString)
    {
        for (String formatString : formatStrings)
        {
            try
            {
                return new SimpleDateFormat(formatString).parse(dateString);
            }
            catch (ParseException e) {
                // log
            }
        }

        return null;
    }

    private String DateToString(Date dateString)
    {
        for (String formatString : formatStrings)
        {
                return new SimpleDateFormat(formatString).format(dateString);
        }

        return null;
    }


}
