package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderACardTest {
    @BeforeEach
    void setUp () {
        open("http://localhost:7777");
    }

    @Test
    void shouldSubmitRequest() {
        $("[data-test-id=name] input").setValue("Тамара Петровна");
        $("[data-test-id=phone] input").setValue("+79267406485");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $(By.className("paragraph")).shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldNotSendInvalidName() {
        $("[data-test-id=name] input").setValue("Tamara Petrovna");
        $("[data-test-id=phone] input").setValue("+79267406485");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSendOverMaxPhoneNumber() {
        $("[data-test-id=name] input").setValue("Тамара Петровна");
        $("[data-test-id=phone] input").setValue("+7926740648500");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldNotSendAboveMinPhoneNumber() {
        $("[data-test-id=name] input").setValue("Тамара Петровна");
        $("[data-test-id=phone] input").setValue("+7926740");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldNotSendWithoutCheckbox() {
        $("[data-test-id=name] input").setValue("Тамара Петровна");
        $("[data-test-id=phone] input").setValue("+79267406485");
        $(By.className("button")).click();
        $(".checkbox_size_m.input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));

    }

    @Test
    void shouldNotSendEmptyName() {
        $("[data-test-id=phone] input").setValue("+79267406485");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSendEmptyPhone() {
        $("[data-test-id=name] input").setValue("Тамара Петровна");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}
