document.getElementById('deleteProfile').addEventListener('click', function () {
    fetch("http://localhost:8000/api/users/delete", {method: 'DELETE'})
        .then(() => window.location.href = 'http://localhost:8000/?success=Profile+Has+Been+Deleted.')
})