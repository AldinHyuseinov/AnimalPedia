document.getElementById('addFact').addEventListener('click', function (event) {
    let fact = document.getElementById('fact').value.trim();

    const data = {
        forAnimalSpecieName: event.target.value,
        fact: fact
    }

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }
    fetch("http://localhost:8000/api/fun-fact/add", requestOptions)
        .then(response => {

            if (response.status === 400) {
                manageResponse(response, data)
            } else {
                location.reload()
            }
        })
})