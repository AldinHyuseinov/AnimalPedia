const form = document.getElementById('edit-form');

form.addEventListener('submit', function (event) {
    event.preventDefault();

    const picture = document.getElementById('picString').value
    const username = document.getElementById('username').value.trim();
    const firstName = document.getElementById('firstName').value.trim();
    const lastName = document.getElementById('lastName').value.trim();
    const email = document.getElementById('email').value.trim();

    const data = {
        imageUrl: picture,
        username: username,
        firstName: firstName,
        lastName: lastName,
        email: email
    }

    const requestOptions = {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }

    fetch("http://localhost:8000/api/users/edit", requestOptions)
        .then(response => {

            if (response.status === 400) {
                manageResponse(response, data)
            } else {
                window.location.href = 'http://localhost:8000/?success=Edit+Was+Successful!';
            }
        });
});
