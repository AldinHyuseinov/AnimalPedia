document.getElementById('undo').addEventListener('click', function (event) {
    fetch("http://localhost:8000/api/animals/unverify/" + event.target.value, {method: 'PATCH'})
        .then(() => location.reload())
})