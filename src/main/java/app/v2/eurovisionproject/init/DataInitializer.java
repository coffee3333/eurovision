package app.v2.eurovisionproject.init;

import app.v2.eurovisionproject.entities.*;
import app.v2.eurovisionproject.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CountryTypeRepository countryTypeRepo;
    private final CountryRepository countryRepo;
    private final UserRoleRepository userRoleRepo;
    private final UserRepository userRepo;
    private final ShowTypeRepository showTypeRepo;
    private final ShowRepository showRepo;
    private final SongRepository songRepo;
    private final ParticipationRepository participationRepo;

    public DataInitializer(CountryTypeRepository countryTypeRepo,
                           CountryRepository countryRepo,
                           UserRoleRepository userRoleRepo,
                           UserRepository userRepo,
                           ShowTypeRepository showTypeRepo,
                           ShowRepository showRepo,
                           SongRepository songRepo,
                           ParticipationRepository participationRepo) {
        this.countryTypeRepo = countryTypeRepo;
        this.countryRepo = countryRepo;
        this.userRoleRepo = userRoleRepo;
        this.userRepo = userRepo;
        this.showTypeRepo = showTypeRepo;
        this.showRepo = showRepo;
        this.songRepo = songRepo;
        this.participationRepo = participationRepo;
    }

    @Override
    public void run(String... args) {
        if (countryRepo.count() > 0) {
            return;
        }

        CountryType big5           = saveCountryType("BIG_5");
        CountryType host           = saveCountryType("HOST");
        CountryType participant    = saveCountryType("PARTICIPANT");
        CountryType nonParticipant = saveCountryType("NON_PARTICIPANT");

        UserRole juryRole   = saveUserRole("JURY");
        UserRole publicRole = saveUserRole("PUBLIC");
        UserRole artistRole = saveUserRole("ARTIST");
        UserRole adminRole  = saveUserRole("ADMIN");

        ShowType sf1Type   = saveShowType("SEMI_FINAL_1");
        ShowType sf2Type   = saveShowType("SEMI_FINAL_2");
        ShowType finalType = saveShowType("GRAND_FINAL");

        Show semiFinal1 = saveShow("Semi-Final 1", LocalDate.of(2026, 5, 12), 2026, sf1Type);
        Show semiFinal2 = saveShow("Semi-Final 2", LocalDate.of(2026, 5, 14), 2026, sf2Type);
        Show grandFinal = saveShow("Grand Final",  LocalDate.of(2026, 5, 16), 2026, finalType);

        Country germany = saveCountry("Germany", "DE", big5);
        Country france = saveCountry("France", "FR", big5);
        Country uk = saveCountry("United Kingdom", "GB", big5);
        Country italy = saveCountry("Italy", "IT", big5);
        Country spain = saveCountry("Spain", "ES", big5);
        Country austria = saveCountry("Austria", "AT", host);

        Country sweden = saveCountry("Sweden", "SE", participant);
        Country norway = saveCountry("Norway", "NO", participant);
        Country finland = saveCountry("Finland", "FI", participant);
        Country iceland = saveCountry("Iceland", "IS", participant);
        Country denmark = saveCountry("Denmark", "DK", participant);
        Country netherlands = saveCountry("Netherlands", "NL", participant);
        Country belgium = saveCountry("Belgium", "BE", participant);
        Country switzerland = saveCountry("Switzerland", "CH", participant);
        Country portugal = saveCountry("Portugal", "PT", participant);
        Country ireland = saveCountry("Ireland", "IE", participant);
        Country israel = saveCountry("Israel", "IL", participant);
        Country cyprus = saveCountry("Cyprus", "CY", participant);
        Country malta = saveCountry("Malta", "MT", participant);
        Country latvia = saveCountry("Latvia", "LV", participant);
        Country lithuania = saveCountry("Lithuania", "LT", participant);

        Country ukraine = saveCountry("Ukraine", "UA", participant);
        Country moldova = saveCountry("Moldova", "MD", participant);
        Country romania = saveCountry("Romania", "RO", participant);
        Country serbia = saveCountry("Serbia", "RS", participant);
        Country croatia = saveCountry("Croatia", "HR", participant);
        Country slovenia = saveCountry("Slovenia", "SI", participant);
        Country czechia = saveCountry("Czech Republic", "CZ", participant);
        Country hungary = saveCountry("Hungary", "HU", participant);
        Country poland = saveCountry("Poland", "PL", participant);
        Country estonia = saveCountry("Estonia", "EE", participant);
        Country armenia = saveCountry("Armenia", "AM", participant);
        Country georgia = saveCountry("Georgia", "GE", participant);
        Country azerbaijan = saveCountry("Azerbaijan", "AZ", participant);
        Country greece = saveCountry("Greece", "GR", participant);
        Country albania = saveCountry("Albania", "AL", participant);

        Song songDE = saveSong("Baller", germany, artistRole);
        Song songFR = saveSong("Ma vie dans tes yeux", france, artistRole);
        Song songGB = saveSong("What the Hell Just Happened?", uk, artistRole);
        Song songIT = saveSong("Tutta l'Italia", italy, artistRole);
        Song songES = saveSong("Esa diva", spain, artistRole);
        Song songAT = saveSong("Wasted", austria, artistRole);

        Song songSE = saveSong("Bara bada bastu", sweden, artistRole);
        Song songNO = saveSong("Lighter", norway, artistRole);
        Song songFI = saveSong("Ich komme", finland, artistRole);
        Song songIS = saveSong("Róa", iceland, artistRole);
        Song songDK = saveSong("Hallucination", denmark, artistRole);
        Song songNL = saveSong("C'est la vie", netherlands, artistRole);
        Song songBE = saveSong("Strobe Lights", belgium, artistRole);
        Song songCH = saveSong("Voyage", switzerland, artistRole);
        Song songPT = saveSong("Deslocado", portugal, artistRole);
        Song songIE = saveSong("Laika Party", ireland, artistRole);
        Song songIL = saveSong("New Day Will Rise", israel, artistRole);
        Song songCY = saveSong("Starlight", cyprus, artistRole);
        Song songMT = saveSong("Loop", malta, artistRole);
        Song songLV = saveSong("Bur mans balss", latvia, artistRole);
        Song songLT = saveSong("Tavo akys", lithuania, artistRole);

        Song songUA = saveSong("Bird of Pray", ukraine, artistRole);
        Song songMD = saveSong("Spotlight", moldova, artistRole);
        Song songRO = saveSong("The Sound of Sunshine", romania, artistRole);
        Song songRS = saveSong("Mila", serbia, artistRole);
        Song songHR = saveSong("Poison Cake", croatia, artistRole);
        Song songSI = saveSong("How Much Time Do We Have Left", slovenia, artistRole);
        Song songCZ = saveSong("Kiss Kiss Goodbye", czechia, artistRole);
        Song songHU = saveSong("Hold Me Closer", hungary, artistRole);
        Song songPL = saveSong("How I'm Feeling Now", poland, artistRole);
        Song songEE = saveSong("Espresso Macchiato", estonia, artistRole);
        Song songAM = saveSong("Survivor", armenia, artistRole);
        Song songGE = saveSong("Freedom", georgia, artistRole);
        Song songAZ = saveSong("Run with the Wind", azerbaijan, artistRole);
        Song songGR = saveSong("Asteromata", greece, artistRole);
        Song songAL = saveSong("Zjerm", albania, artistRole);

        addParticipation(songDE, grandFinal);
        addParticipation(songFR, grandFinal);
        addParticipation(songGB, grandFinal);
        addParticipation(songIT, grandFinal);
        addParticipation(songES, grandFinal);
        addParticipation(songAT, grandFinal);

        List<Song> sf1Songs = List.of(
                songSE, songNO, songFI, songIS, songDK,
                songNL, songBE, songCH, songPT, songIE,
                songIL, songCY, songMT, songLV, songLT
        );
        for (Song s : sf1Songs) {
            addParticipation(s, semiFinal1);
            addParticipation(s, grandFinal);
        }

        List<Song> sf2Songs = List.of(
                songUA, songMD, songRO, songRS, songHR,
                songSI, songCZ, songHU, songPL, songEE,
                songAM, songGE, songAZ, songGR, songAL
        );
        for (Song s : sf2Songs) {
            addParticipation(s, semiFinal2);
            addParticipation(s, grandFinal);
        }

        saveUser("jury_de", germany, juryRole);
        saveUser("jury_fr", france,  juryRole);
        saveUser("jury_gb", uk,      juryRole);
        saveUser("jury_it", italy,   juryRole);
        saveUser("jury_es", spain,   juryRole);
        saveUser("jury_at", austria, juryRole);
        saveUser("jury_se", sweden,      juryRole);
        saveUser("jury_no", norway,      juryRole);
        saveUser("jury_fi", finland,     juryRole);
        saveUser("jury_is", iceland,     juryRole);
        saveUser("jury_dk", denmark,     juryRole);
        saveUser("jury_nl", netherlands, juryRole);
        saveUser("jury_be", belgium,     juryRole);
        saveUser("jury_ch", switzerland, juryRole);
        saveUser("jury_pt", portugal,    juryRole);
        saveUser("jury_ie", ireland,     juryRole);
        saveUser("jury_il", israel,      juryRole);
        saveUser("jury_cy", cyprus,      juryRole);
        saveUser("jury_mt", malta,       juryRole);
        saveUser("jury_lv", latvia,      juryRole);
        saveUser("jury_lt", lithuania,   juryRole);
        saveUser("jury_ua", ukraine,     juryRole);
        saveUser("jury_md", moldova,     juryRole);
        saveUser("jury_ro", romania,     juryRole);
        saveUser("jury_rs", serbia,      juryRole);
        saveUser("jury_hr", croatia,     juryRole);
        saveUser("jury_si", slovenia,    juryRole);
        saveUser("jury_cz", czechia,     juryRole);
        saveUser("jury_hu", hungary,     juryRole);
        saveUser("jury_pl", poland,      juryRole);
        saveUser("jury_ee", estonia,     juryRole);
        saveUser("jury_am", armenia,     juryRole);
        saveUser("jury_ge", georgia,     juryRole);
        saveUser("jury_az", azerbaijan,  juryRole);
        saveUser("jury_gr", greece,      juryRole);
        saveUser("jury_al", albania,     juryRole);

        saveUser("public_de", germany, publicRole);
        saveUser("public_fr", france,  publicRole);
        saveUser("public_gb", uk,      publicRole);
        saveUser("public_it", italy,   publicRole);
        saveUser("public_es", spain,   publicRole);
        saveUser("public_at", austria, publicRole);
        saveUser("public_se", sweden,      publicRole);
        saveUser("public_no", norway,      publicRole);
        saveUser("public_fi", finland,     publicRole);
        saveUser("public_is", iceland,     publicRole);
        saveUser("public_dk", denmark,     publicRole);
        saveUser("public_nl", netherlands, publicRole);
        saveUser("public_be", belgium,     publicRole);
        saveUser("public_ch", switzerland, publicRole);
        saveUser("public_pt", portugal,    publicRole);
        saveUser("public_ie", ireland,     publicRole);
        saveUser("public_il", israel,      publicRole);
        saveUser("public_cy", cyprus,      publicRole);
        saveUser("public_mt", malta,       publicRole);
        saveUser("public_lv", latvia,      publicRole);
        saveUser("public_lt", lithuania,   publicRole);
        saveUser("public_ua", ukraine,     publicRole);
        saveUser("public_md", moldova,     publicRole);
        saveUser("public_ro", romania,     publicRole);
        saveUser("public_rs", serbia,      publicRole);
        saveUser("public_hr", croatia,     publicRole);
        saveUser("public_si", slovenia,    publicRole);
        saveUser("public_cz", czechia,     publicRole);
        saveUser("public_hu", hungary,     publicRole);
        saveUser("public_pl", poland,      publicRole);
        saveUser("public_ee", estonia,     publicRole);
        saveUser("public_am", armenia,     publicRole);
        saveUser("public_ge", georgia,     publicRole);
        saveUser("public_az", azerbaijan,  publicRole);
        saveUser("public_gr", greece,      publicRole);
        saveUser("public_al", albania,     publicRole);
    }

    private CountryType saveCountryType(String name) {
        CountryType ct = new CountryType();
        ct.setName(name);
        return countryTypeRepo.save(ct);
    }

    private UserRole saveUserRole(String name) {
        UserRole r = new UserRole();
        r.setName(name);
        return userRoleRepo.save(r);
    }

    private ShowType saveShowType(String name) {
        ShowType st = new ShowType();
        st.setName(name);
        return showTypeRepo.save(st);
    }

    private Country saveCountry(String name, String code, CountryType type) {
        Country c = new Country();
        c.setName(name);
        c.setCode(code);
        c.setCountryType(type);
        return countryRepo.save(c);
    }

    private Show saveShow(String name, LocalDate date, int year, ShowType type) {
        Show s = new Show();
        s.setName(name);
        s.setDate(date);
        s.setYear(year);
        s.setShowType(type);
        return showRepo.save(s);
    }

    private Song saveSong(String title, Country country, UserRole artistRole) {
        User artist = new User();
        artist.setUsername("artist_" + country.getCode().toLowerCase());
        artist.setPasswordHash("placeholder");
        artist.setRole(artistRole);
        artist.setCountry(country);
        userRepo.save(artist);

        Song song = new Song();
        song.setTitle(title);
        song.setArtist(artist);
        song.setCountry(country);
        return songRepo.save(song);
    }

    private void addParticipation(Song song, Show show) {
        Participation p = new Participation();
        p.setSong(song);
        p.setShow(show);
        participationRepo.save(p);
    }

    private void saveUser(String username, Country country, UserRole role) {
        User u = new User();
        u.setUsername(username);
        u.setPasswordHash("demo");
        u.setRole(role);
        u.setCountry(country);
        userRepo.save(u);
    }
}
