package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Employee;
import utils.DBUtil;

public class EmployeeValidator {
    public static List<String> validate(Employee e, Boolean code_duplicate_check_flag, Boolean password_check_flag) {
        List<String> errors = new ArrayList<String>();

        String code_error = _validateCode(e.getCode(), code_duplicate_check_flag);
        if(!code_error.equals("")) {
            errors.add(code_error);
        }

        String name_error = _validateName(e.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        String password_error = _validatePassword(e.getPassword(), password_check_flag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        return errors;
    }

    // 社員番号
    private static String _validateCode(String code, Boolean code_duplicate_check_flag) {
        // 必須入力チェック
        if(code == null || code.equals("")) {
            return "社員番号を入力してください。";
        }

        // 既に登録されている社員番号との重複チェック
        if(code_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long employees_count = (long)em.createNamedQuery("checkRegisteredCode", Long.class)
                                           .setParameter("code", code)
                                             .getSingleResult();
            em.close();
            if(employees_count > 0) {
                return "入力された社員番号の情報は既に存在しています。";//ここでガードをかけるのは？
            }
        }

        return "";
    }

    // 社員名の必須入力チェック
    private static String _validateName(String name) {
        if(name == null || name.equals("")) {
            return "氏名を入力してください。";
        }

        return "";
    }

    // パスワードの必須入力チェック
    private static String _validatePassword(String password, Boolean password_check_flag) {
        // パスワードを変更する場合のみ実行
        if(password_check_flag && (password == null || password.equals(""))) {
            return "パスワードを入力してください。";
        }
        return "";
    }
}
/*
 * 一般的な会員サイトでは、登録情報を変更するとき「パスワードは変更する場合のみ入力してください」と表示されます。
 * それと同じように、日報管理システムでも新規登録（create）の場合はパスワードの入力値チェックをしたい、でも変更（update）の場合はパスワードの入力が無いのに入力値チェックが実行されるのは困ります。
 * 社員番号も同様で、変更しないのに重複チェックが実行されるとエラーとなって変更できなくなります。つまり、変更（update）の場合は、バリデーションが不要な場合があるのです。

そこで、社員番号とパスワードについては、第2引数にBoolean型の引数を用意し、そこが true であればパスワードの入力値チェック、および社員番号の重複チェックを行うようにしています。
後ほど作成するコントローラの方でフォームの入力状態を確認し、バリデーションを実行する or しないを決めます。
 */
