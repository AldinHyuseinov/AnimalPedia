const factButton = document.getElementById('randomFactButton')

factButton.addEventListener('click', function (event) {
    fetch("http://localhost:8000/api/fun-fact/" + event.target.value, {method: 'GET'})
        .then(response => {
            const fact = document.getElementById('randomFact')
            response.text().then(data => fact.innerHTML = data)

            if (response.status === 200) {
                factButton.innerHTML = 'See More!'
            } else if (response.status === 404) {
                factButton.remove()
            }
        })
})