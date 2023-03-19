const picture = document.getElementById('picture');
const picString = document.getElementById('picString');

picture.addEventListener('change', function(event) {
    if (event.target === picture) {
        picString.value = picture.value;
        picToString();
    }
});
