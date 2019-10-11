package com.todo1.hulkstore.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"productId", "description"})
@ToString
public class Product implements Serializable {

  @Id
  @GeneratedValue
  private Long productId;

  @NotNull
  @NotEmpty
  private String description;

  @DecimalMin(value = "0")
  private BigDecimal amount;

  @DecimalMin(value = "0")
  private BigDecimal price;

  private LocalDateTime creationDate;
}
