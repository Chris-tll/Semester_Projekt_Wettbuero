document.addEventListener('DOMContentLoaded', function () {
     /*-------------------HORSERACE-------------------*/
     const quadratHorserace = document.getElementById('quadrat_horserace');
     const horseTitle = document.getElementById('horserace_title');
     const horseText = document.getElementById('horserace_text');

     /*-------------------DOGRACE-------------------*/
     const quadratDograce = document.getElementById('quadrat_dograce');
     const dograceTitle = document.getElementById('dograce_title');
     const dograceText = document.getElementById('dograce_text');

     /*-------------------SNAILRACE-------------------*/
     const quadratSnailrace = document.getElementById('quadrat_snailrace');
     const snailraceTitle = document.getElementById('snailrace_title');
     const snailraceText = document.getElementById('snailrace_text');
        
    /*-------------------HORSERACE-------------------*/ 
    horseText.addEventListener('click', function() {
        quadratHorserace.classList.add('active');
        horseTitle.classList.add('active');
        horseTitle.textContent = "Horserace:";

        horseText.textContent = "Horse racing is a competitive sport where horses, ridden by jockeys, race against each other over a set distance. It is one of the oldest sports, dating back thousands of years. The races can take place on flat tracks or over obstacles, known as steeplechases. Horse racing is known for its excitement, speed, and the bond between the horse and jockey. Major events like the Kentucky Derby and Royal Ascot attract global audiences and significant betting interest."
        horseText.classList.add('active');
     });

     /*-------------------DOGRACE-------------------*/
     dograceText.addEventListener('click', function() {
          quadratDograce.classList.add('active');
          dograceTitle.classList.add('active');
          dograceTitle.textContent = "Dograce:";
  
          dograceText.textContent = "Dog racing is a popular sport where specially trained dogs, most commonly greyhounds, race around an oval or straight track with the aim of crossing the finish line first. These events are often held at dedicated racetracks equipped with a mechanical lure that the dogs chase, simulating the pursuit of prey. The sport has a rich history and is particularly prominent in countries like the United States, the United Kingdom, and Australia. Spectators place bets on the outcomes of the races, adding a layer of excitement and financial interest. While dog racing is enjoyed by many for its thrill and entertainment value, it has also been the subject of controversy and scrutiny due to concerns about the welfare and treatment of the racing dogs, leading to calls for stricter regulations and, in some places, bans on the sport."
          dograceText.classList.add('active');
       });

       /*-------------------SNAILRACE-------------------*/
       snailraceText.addEventListener('click', function() {
          quadratSnailrace.classList.add('active');
          snailraceTitle.classList.add('active');
          snailraceTitle.textContent = "Snailrace:";
  
          snailraceText.textContent = "A snail race is a whimsical and light-hearted competition where snails are placed on a track and race over a short distance, typically marked with concentric circles or a straight path. Participants and spectators watch as the snails slowly make their way towards the finish line, often encouraged by gentle cheering and creative track designs. These races are usually held for fun at events like garden parties, fairs, or educational activities, offering a charming contrast to faster-paced races like those of dogs or horses. Despite the slow pace, snail races generate excitement and amusement as people cheer on their chosen snails, enjoying the unique and quirky nature of the event."
          snailraceText.classList.add('active');
       });
});
    
    
