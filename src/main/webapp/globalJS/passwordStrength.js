/* 
 * Modified version of this password strength indicator "http://www.paulund.co.uk/password-strength-indicator-jquery" thanks 
 * to the original dev for providing it!
 */


//var globalValues.passwordValid;

//$(document).ready(function () {
//    $('#password, #confirmPassword').on('keyup', function (event) {
//        if ($('#password').val() == '' && $('#confirmPassword').val() == '')
//        {
//            //$('#passwordStrength').removeClass().html('');
//            document.getElementById('passwordStrength').innerHTML = "";
//
//            globalValues.passwordValid = false;
//            return false;
//        }
//        if ($('#password').val() != '' && $('#confirmPassword').val() != '' && $('#password').val() != $('#confirmPassword').val())
//        {
//            //$('#passwordStrength').removeClass().addClass('alert alert-danger').html('Passwords do not match!');
//            document.getElementById('passwordStrength').innerHTML = "<div class='alert alert-danger' role='alert'>Passwords do not match</div>";
//
//            //$("#passwordFormSubmitButton").click(preventDefault(event));
//            globalValues.passwordValid = false;
//            return false;
//        }
//        // Must have capital letter, numbers and lowercase letters
//        var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
//        // Must have either capitals and lowercase letters or lowercase and numbers
//        var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
//        // Must be at least 6 characters long
//        var okRegex = new RegExp("(?=.{6,}).*", "g");
//        if (okRegex.test($(this).val()) === false) {
//            // If ok regex doesn't match the password
//            //$('#passwordStrength').removeClass().addClass('alert alert-danger').html('Password must be at least 6 characters long.');
//            document.getElementById('passwordStrength').innerHTML = "<div class='alert alert-danger' role='alert'>Password must be at least 6 characters long</div>";
//            globalValues.passwordValid = false;
//        } else if (strongRegex.test($(this).val())) {
//            // If reg ex matches strong password
//            //$('#passwordStrength').removeClass().addClass('alert alert-success').html('Good Password!');
//            document.getElementById('passwordStrength').innerHTML = "<div class='alert alert-success' role='alert'>Good Password</div>";
//
//            globalValues.passwordValid = true;
//        } else if (mediumRegex.test($(this).val())) {
//            // If medium password matches the reg ex
//            //$('#passwordStrength').removeClass().addClass('alert alert-success').html('Fair password, it can be stronger with more capital letters, more numbers and special characters!');
//            document.getElementById('passwordStrength').innerHTML = "<div class='alert alert-success' role='alert'>Fair password, it can be stronger with more capital letters, more numbers and special characters</div>";
//
//            globalValues.passwordValid = true;
//        } else {
//            // If password is ok
//            //$('#passwordStrength').removeClass().addClass('alert alert-info').html('Weak Password, try adding numbers and capital letters.');
//            document.getElementById('passwordStrength').innerHTML = "<div class='alert alert-info' role='alert'>Weak Password, try adding numbers and capital letters</div>"
//
//            globalValues.passwordValid = true;
//        }
//    });
//});

function passwordStrengthTester(passwordID, confirmPasswordID, feedbackDivID)
{
    passwordID = "#"+ passwordID;
    confirmPasswordID = "#"+ confirmPasswordID;
    $(passwordID+','+confirmPasswordID).on('keyup', function (event) {
        if ($(passwordID).val() == '' && $(confirmPasswordID).val() == '')
        {
            //$('#passwordStrength').removeClass().html('');
            document.getElementById(feedbackDivID).innerHTML = "";

            globalValues.passwordValid = false;
            return false;
        }
        if ($(passwordID).val() != '' && $(confirmPasswordID).val() != '' && $(passwordID).val() != $(confirmPasswordID).val())
        {
            //$('#passwordStrength').removeClass().addClass('alert alert-danger').html('Passwords do not match!');
            document.getElementById(feedbackDivID).innerHTML = "<div class='alert alert-danger' role='alert'>Passwords do not match</div>";

            //$("#passwordFormSubmitButton").click(preventDefault(event));
            globalValues.passwordValid = false;
            return false;
        }
        // Must have capital letter, numbers and lowercase letters
        var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
        // Must have either capitals and lowercase letters or lowercase and numbers
        var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
        // Must be at least 6 characters long
        var okRegex = new RegExp("(?=.{6,}).*", "g");
        if (okRegex.test($(this).val()) === false) {
            // If ok regex doesn't match the password
            //$('#passwordStrength').removeClass().addClass('alert alert-danger').html('Password must be at least 6 characters long.');
            document.getElementById(feedbackDivID).innerHTML = "<div class='alert alert-danger' role='alert'>Password must be at least 6 characters long</div>";
            globalValues.passwordValid = false;
        } else if (strongRegex.test($(this).val())) {
            // If reg ex matches strong password
            //$('#passwordStrength').removeClass().addClass('alert alert-success').html('Good Password!');
            document.getElementById(feedbackDivID).innerHTML = "<div class='alert alert-success' role='alert'>Good Password</div>";

            globalValues.passwordValid = true;
        } else if (mediumRegex.test($(this).val())) {
            // If medium password matches the reg ex
            //$('#passwordStrength').removeClass().addClass('alert alert-success').html('Fair password, it can be stronger with more capital letters, more numbers and special characters!');
            document.getElementById(feedbackDivID).innerHTML = "<div class='alert alert-success' role='alert'>Fair password, it can be stronger with more capital letters, more numbers and special characters</div>";

            globalValues.passwordValid = true;
        } else {
            // If password is ok
            //$('#passwordStrength').removeClass().addClass('alert alert-info').html('Weak Password, try adding numbers and capital letters.');
            document.getElementById(feedbackDivID).innerHTML = "<div class='alert alert-info' role='alert'>Weak Password, try adding numbers and capital letters</div>"

            globalValues.passwordValid = true;
        }
    });
}
//function submitIfPasswordValid()
//{
//    console.log("password is valid?: " + globalValues.passwordValid);
//    
//    return globalValues.passwordValid;
//}


















//$(document).ready(function () {
//                $('#password, #confirmPassword').on('keyup', function (event) {
//                    if ($('#password').val() == '' && $('#confirmPassword').val() == '')
//                    {
//                        $('#passwordStrength').removeClass().html('');
//                        return false;
//                    }
//                    if ($('#password').val() != '' && $('#confirmPassword').val() != '' && $('#password').val() != $('#confirmPassword').val())
//                    {
//                        $('#passwordStrength').removeClass().addClass('alert alert-danger').html('Passwords do not match!');
//                        $("#passwordFormSubmitButton").click(preventDefault(event));
//                        return false;
//                    }
//                    // Must have capital letter, numbers and lowercase letters
//                    var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
//                    // Must have either capitals and lowercase letters or lowercase and numbers
//                    var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
//                    // Must be at least 6 characters long
//                    var okRegex = new RegExp("(?=.{6,}).*", "g");
//                    if (okRegex.test($(this).val()) === false) {
//                        // If ok regex doesn't match the password
//                        $('#passwordStrength').removeClass().addClass('alert alert-danger').html('Password must be at least 6 characters long.');
//                        $("#passwordFormSubmitButton").click(preventDefault(event));
//                    } else if (strongRegex.test($(this).val())) {
//                        // If reg ex matches strong password
//                        $('#passwordStrength').removeClass().addClass('alert alert-success').html('Good Password!');
//                        $("#passwordFormSubmitButton").click(enableDefault());
//                    } else if (mediumRegex.test($(this).val())) {
//                        // If medium password matches the reg ex
//                        $('#passwordStrength').removeClass().addClass('alert alert-success').html('Fair password, it can be stronger with more capital letters, more numbers and special characters!');
//                        $("#passwordFormSubmitButton").click(enableDefault());
//                    } else {
//                        // If password is ok
//                        $('#passwordStrength').removeClass().addClass('alert alert-info').html('Weak Password, try adding numbers and capital letters.');
//                        $("#passwordFormSubmitButton").click(enableDefault());
//                    }
//                    return true;
//                });
//            });
//            function preventDefault(event)
//            {
//                event.preventDefault();
//            }
//            function enableDefault()
//            {
//                $('#passwordFormSubmitButton').unbind('click');
//            }
