const form = document.getElementById('animal-form');

form.addEventListener('submit', function (event) {
    event.preventDefault();

    const data = animalData()

    const requestOptions = {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }

    fetch("http://localhost:8000/api/animals/edit", requestOptions)
        .then(response => {

            if (response.status === 400) {
                manageResponse(response, data)
            } else {
                location.reload()
            }
        });
});