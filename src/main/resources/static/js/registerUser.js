const form = document.getElementById('register-form');

form.addEventListener('submit', function (event) {
    event.preventDefault();

    const username = document.getElementById('username').value.trim();
    const firstName = document.getElementById('firstName').value.trim();
    const lastName = document.getElementById('lastName').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const confirmPassword = document.getElementById('confirmPassword').value.trim();

    const data = {
        username: username,
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password,
        confirmPassword: confirmPassword
    }

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }

    fetch("http://localhost:8000/api/users/register", requestOptions)
        .then(response => {

            if (response.status === 400) {
                response.json().then(errors => {
                    Object.keys(errors).forEach(key => {
                        const errorElement = document.querySelector(`#${key}-error`);
                        errorElement.textContent = errors[key];
                        errorElement.style.display = 'block';
                    });

                    for (let dataKey in data) {

                        if (!errors.hasOwnProperty(dataKey)) {
                            document.querySelector(`#${dataKey}-error`).style.display = 'none';
                        }
                    }
                })
            } else {
                window.location.href = 'http://localhost:8000/';
            }
        });
});
