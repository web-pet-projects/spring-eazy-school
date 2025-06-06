Excellent — this is a **very common and subtle issue** with Spring’s `@Valid` mechanism when working with nested DTOs.

Let’s rephrase your concern professionally:

> “I have a `PersonDTO` that contains an `AddressDTO`, both with validation annotations. But the validations inside `AddressDTO` are not being triggered when I validate `PersonDTO`. Why?”

---

## 🔍 Root Cause

By default, **Spring Validation does NOT cascade into nested objects** unless you explicitly annotate them with `@Valid`.

---

## ✅ Fix: Add `@Valid` to the nested object field

In your `PersonDTO`, you need:

```java
@Valid
private AddressDTO address;
```

This tells Spring:

> “Please validate this nested object too — and any constraints inside it.”

---

### ✅ Full Example

#### 📦 `PersonDTO.java`

```java
public class PersonDTO {

    @NotBlank
    private String firstName;

    @Valid  // ✅ this enables recursive validation
    private AddressDTO address;

    // getters/setters
}
```

#### 📦 `AddressDTO.java`

```java
public class AddressDTO {

    @NotBlank
    private String city;

    @Size(min = 5, max = 10)
    private String zipCode;

    // getters/setters
}
```

Without `@Valid`, Spring will:

* Validate `PersonDTO`
* Skip over `AddressDTO` silently

---

## ✅ Works with:

* Spring MVC controllers using `@ModelAttribute` or `@RequestBody`
* Method parameters annotated with `@Valid` or `@Validated`
* JSR-380 (Jakarta Bean Validation 3.0 or Hibernate Validator)

---

## 🧠 Summary

| Symptom                                  | Cause                            | Fix                                               |
| ---------------------------------------- | -------------------------------- | ------------------------------------------------- |
| Nested DTO validations are not triggered | Missing `@Valid` on nested field | Add `@Valid` on `AddressDTO` field in `PersonDTO` |

---

Would you like me to help configure a `@RestControllerAdvice` to return a structured JSON error response like `{ field: message }` even for nested DTOs (e.g. `address.city`)? Great for API clarity.
