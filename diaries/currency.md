Working with **currency** in Spring Boot involves handling **monetary values** in a way that is **accurate, type-safe, locale-aware**, and **consistent** across your:

* **Entity (`@Entity`)**
* **DTO (`@Data`)**
* **Controller input/output**
* **Template/UI display**
* **Persistence layer (DB)**

Let’s walk through **best practices** step by step 👇

---

## 🧾 1. Use `BigDecimal` in Your Model

Never use `double` or `float` for currency — they’re not precise enough.

```java
@Entity
public class Product {
    @Id
    private Long id;

    private String name;

    @Column(precision = 12, scale = 2) // For example: 999,999,999.99
    private BigDecimal price;
}
```

> `precision = total digits`, `scale = digits after decimal`

---

## 📤 2. DTO Field Should Also Be `BigDecimal`

```java
public class ProductDTO {
    private String name;

    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
}
```

> Add `@Digits` to validate numeric format on input (especially for `@Valid` binding)

---

## 🔄 3. Map Entity ↔ DTO with ModelMapper (or Manually)

```java
modelMapper.map(productDTO, Product.class);
```

Or:

```java
Product product = new Product();
product.setPrice(new BigDecimal("29.99"));
```

---

## 🌍 4. Format Currency in Thymeleaf

```html
<td th:text="${#numbers.formatCurrency(product.price)}"></td>
```

You can also specify a currency locale manually:

```html
<td th:text="${#numbers.formatDecimal(product.price, 1, 'COMMA', 2, 'POINT')}"></td>
```

> `#numbers.formatCurrency(...)` respects the server's default locale

---

## 🌐 5. Customize Currency Formatting by Locale

In your Spring config:

```java
@Bean
public LocaleResolver localeResolver() {
    SessionLocaleResolver resolver = new SessionLocaleResolver();
    resolver.setDefaultLocale(Locale.US); // or Locale.forLanguageTag("vi-VN")
    return resolver;
}
```

---

## 💵 6. Optional: Use JavaMoney (JSR 354) for Richer API

If you need to support **multi-currency apps**, use:

```xml
<dependency>
    <groupId>org.javamoney</groupId>
    <artifactId>moneta</artifactId>
</dependency>
```

And model like:

```java
private MonetaryAmount price; // like "USD 29.99"
```

But for most cases, `BigDecimal` is sufficient.

---

## ✅ Summary Table

| Layer     | Type         | Best Practice                             |
| --------- | ------------ | ----------------------------------------- |
| Entity    | `BigDecimal` | Annotate with `@Column(precision, scale)` |
| DTO       | `BigDecimal` | Annotate with `@Digits`                   |
| Thymeleaf | `#numbers`   | `formatCurrency(...)`                     |
| REST API  | JSON         | Send as plain number, e.g., `29.99`       |
| Locale    | Config       | Define default or user-specific locale    |
| Advanced  | JavaMoney    | Use `MonetaryAmount` if multi-currency    |

---

Would you like a working example (DTO + Entity + Controller + HTML) to display and update product prices using currency format?
