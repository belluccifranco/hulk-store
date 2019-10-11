package com.todo1.hulkstore.model.dto;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

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
