gulp = require('gulp');
javac = require('gulp-javac');

gulp.task('build', function () {
    return gulp.src('./src/**/*.java')
    .pipe(javac.javac())
    .pipe(gulp.dest('bin'));
});
