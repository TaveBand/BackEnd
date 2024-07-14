package ys_band.develop.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ys_band.develop.domain.Authority;
import ys_band.develop.domain.User;
import ys_band.develop.dto.UserDTO;
import ys_band.develop.repository.UserRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }


    @Transactional
    public UserDTO register(UserDTO userDTO) throws Exception{
        if (userRepository.findByUsername(userDTO.getUsername()).orElse(null)!=null) {
            throw new  Exception("이미 가입되어 있는 사용자입니다.");
        }
        // 이메일에서 도메인 부분 추출
        String email = userDTO.getEmail();
        String domain = email.substring(email.indexOf("@") + 1);

        // 학교 ID 설정
        String schoolId = schoolEmailMap.get(domain);
        if (schoolId == null) {
            throw new Exception("유효하지 않은 학교 이메일입니다.");
        }
        //권한 부여
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        //사용자 생성 및 저장
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setNickname(userDTO.getNickname());
        user.setEmail(userDTO.getEmail());
        user.setSchoolId(schoolId);
        user.setAuthorities(Collections.singleton(authority));




        return UserDTO.fromEntity(userRepository.save(user));
    }

    // 이메일 도메인과 학교 이름을 매핑하는 맵 초기화
    private static final Map<String, String> schoolEmailMap = new HashMap<>();

    static {
        schoolEmailMap.put("kaya.ac.kr", "가야대학교");
        schoolEmailMap.put("gachon.ac.kr", "가천대학교");
        schoolEmailMap.put("catholic.ac.kr", "가톨릭대학교");
        schoolEmailMap.put("mtu.ac.kr", "감리교신학대학교");
        schoolEmailMap.put("kangnam.ac.kr", "강남대학교");
        schoolEmailMap.put("gwnu.ac.kr", "강릉원주대학교");
        schoolEmailMap.put("kangwon.ac.kr", "강원대학교");
        schoolEmailMap.put("konkuk.ac.kr", "건국대학교");
        schoolEmailMap.put("kku.ac.kr", "건국대학교(글로컬)");
        schoolEmailMap.put("konyang.ac.kr", "건양대학교");
        schoolEmailMap.put("kycu.ac.kr", "건양사이버대학교");
        schoolEmailMap.put("kyonggi.ac.kr", "경기대학교");
        schoolEmailMap.put("gntech.ac.kr", "경남과학기술대학교");
        schoolEmailMap.put("hanma.kr", "경남대학교");
        schoolEmailMap.put("k1.ac.kr", "경동대학교");
        schoolEmailMap.put("knu.ac.kr", "경북대학교");
        schoolEmailMap.put("kufs.ac.kr", "경북외국어대학교");
        schoolEmailMap.put("gnu.ac.kr", "경상대학교");
        schoolEmailMap.put("ks.ac.kr", "경성대학교");
        schoolEmailMap.put("ikw.ac.kr", "경운대학교");
        schoolEmailMap.put("ginue.ac.kr", "경인교육대학교");
        schoolEmailMap.put("kiu.kr", "경일대학교");
        schoolEmailMap.put("gju.ac.kr", "경주대학교");
        schoolEmailMap.put("khu.ac.kr", "경희대학교");
        schoolEmailMap.put("khcu.ac.kr", "경희사이버대학교");
        schoolEmailMap.put("kmu.ac.kr", "계명대학교");
        schoolEmailMap.put("korea.ac.kr", "고려대학교");
        schoolEmailMap.put("cuk.edu", "고려사이버대학교");
        schoolEmailMap.put("kosin.ac.kr", "고신대학교");
        schoolEmailMap.put("gjue.ac.kr", "공주교육대학교");
        schoolEmailMap.put("kongju.ac.kr", "공주대학교");
        schoolEmailMap.put("cku.ac.kr", "가톨릭관동대학교");
        schoolEmailMap.put("kwangshin.ac.kr", "광신대학교");
        schoolEmailMap.put("kw.ac.kr", "광운대학교");
        schoolEmailMap.put("kjcatholic.ac.kr", "광주가톨릭대학교");
        schoolEmailMap.put("gist.ac.kr", "GIST");
        schoolEmailMap.put("gnue.ac.kr", "광주교육대학교");
        schoolEmailMap.put("gwangju.ac.kr", "광주대학교");
        schoolEmailMap.put("kwu.ac.kr", "광주여자대학교");
        schoolEmailMap.put("kookmin.ac.kr", "국민대학교");
        schoolEmailMap.put("gcu.ac", "국제사이버대학교");
        schoolEmailMap.put("kunsan.ac.kr", "군산대학교");
        schoolEmailMap.put("kdu.ac.kr", "극동대학교");
        schoolEmailMap.put("global.ac.kr", "글로벌사이버대학교");
        schoolEmailMap.put("ggu.ac.kr", "금강대학교");
        schoolEmailMap.put("kumoh.ac.kr", "금오공과대학교");
        schoolEmailMap.put("gimcheon.ac.kr", "김천대학교");
        schoolEmailMap.put("kkot.ac.kr", "꽃동네대학교");
        schoolEmailMap.put("kornu.ac.kr", "나사렛대학교");
        schoolEmailMap.put("nambu.ac.kr", "남부대학교");
        schoolEmailMap.put("nsu.ac.kr", "남서울대학교");
        schoolEmailMap.put("dankook.ac.kr", "단국대학교");
        schoolEmailMap.put("cu.ac.kr", "대구가톨릭대학교");
        schoolEmailMap.put("dgist.ac.kr", "DGIST");
        schoolEmailMap.put("dnue.ac.kr", "대구교육대학교");
        schoolEmailMap.put("daegu.ac.kr", "대구대학교");
        schoolEmailMap.put("dcu.ac.kr", "대구사이버대학교");
        schoolEmailMap.put("dgau.ac.kr", "대구예술대학교");
        schoolEmailMap.put("dufs.ac.kr", "대구외국어대학교");
        schoolEmailMap.put("dhu.ac.kr", "대구한의대학교");
        schoolEmailMap.put("daeshin.ac.kr", "대신대학교");
        schoolEmailMap.put("dcatholic.ac.kr", "대전가톨릭대학교");
        schoolEmailMap.put("dju.ac.kr", "대전대학교");
        schoolEmailMap.put("daejeon.ac.kr", "대전신학교");
        schoolEmailMap.put("daejin.ac.kr", "대진대학교");
        schoolEmailMap.put("duksung.ac.kr", "덕성여자대학교");
        schoolEmailMap.put("dongguk.edu", "동국대학교");
        schoolEmailMap.put("dongguk.ac.kr", "동국대학교(경주)");
        schoolEmailMap.put("dongduk.ac.kr", "동덕여자대학교");
        schoolEmailMap.put("dongseo.ac.kr", "동서대학교");
        schoolEmailMap.put("dsu.kr", "동신대학교");
        schoolEmailMap.put("donga.ac.kr", "동아대학교");
        schoolEmailMap.put("dyu.ac.kr", "동양대학교");
        schoolEmailMap.put("deu.ac.kr", "동의대학교");
        schoolEmailMap.put("scau.ac.kr", "디지털서울문화예술대학교");
        schoolEmailMap.put("ltu.ac.kr", "루터대학교");
        schoolEmailMap.put("mju.ac.kr", "명지대학교");
        schoolEmailMap.put("mokwon.ac.kr", "목원대학교");
        schoolEmailMap.put("mcu.ac.kr", "목포가톨릭대학교");
        schoolEmailMap.put("mokpo.ac.kr", "목포대학교");
        schoolEmailMap.put("mmu.ac.kr", "목포해양대학교");
        schoolEmailMap.put("pcu.ac.kr", "배재대학교");
        schoolEmailMap.put("bu.ac.kr", "백석대학교");
        schoolEmailMap.put("pukyong.ac.kr", "부경대학교");
        schoolEmailMap.put("cup.ac.kr", "부산가톨릭대학교");
        schoolEmailMap.put("bnue.ac.kr", "부산교육대학교");
        schoolEmailMap.put("pusan.ac.kr", "부산대학교");
        schoolEmailMap.put("bdu.ac.kr", "부산디지털대학교");
        schoolEmailMap.put("bufs.ac.kr", "부산외국어대학교");
        schoolEmailMap.put("bpu.ac.kr", "부산장신대학교");
        schoolEmailMap.put("cufs.ac.kr", "사이버한국외국어대학교");
        schoolEmailMap.put("syuin.ac.kr", "삼육대학교");
        schoolEmailMap.put("sangmyung.kr", "상명대학교");
        schoolEmailMap.put("sangji.ac.kr", "상지대학교");
        schoolEmailMap.put("sogang.ac.kr", "서강대학교");
        schoolEmailMap.put("skuniv.ac.kr", "서경대학교");
        schoolEmailMap.put("seonam.ac.kr", "서남대학교");
        schoolEmailMap.put("seoultech.ac.kr", "서울과학기술대학교");
        schoolEmailMap.put("snue.ac.kr", "서울교육대학교");
        schoolEmailMap.put("scu.ac.kr", "서울기독대학교");
        schoolEmailMap.put("snu.ac.kr", "서울대학교");
        schoolEmailMap.put("sdu.ac.kr", "서울디지털대학교");
        schoolEmailMap.put("iscu.ac.kr", "서울사이버대학교");
        schoolEmailMap.put("uos.ac.kr", "서울시립대학교");
        schoolEmailMap.put("stu.ac.kr", "서울신학대학교");
        schoolEmailMap.put("swu.ac.kr", "서울여자대학교");
        schoolEmailMap.put("sjs.ac.kr", "서울장신대학교");
        schoolEmailMap.put("seowon.ac.kr", "서원대학교");
        schoolEmailMap.put("sunmoon.ac.kr", "선문대학교");
        schoolEmailMap.put("sungkyul.ac.kr", "성결대학교");
        schoolEmailMap.put("skhu.ac.kr", "성공회대학교");
        schoolEmailMap.put("skku.edu", "성균관대학교");
        schoolEmailMap.put("sungshin.ac.kr", "성신여자대학교");
        schoolEmailMap.put("semyung.ac.kr", "세명대학교");
        schoolEmailMap.put("sju.ac.kr", "세종대학교");
        schoolEmailMap.put("sjcu.ac.kr", "세종사이버대학교");
        schoolEmailMap.put("sehan.ac.kr", "세한대학교");
        schoolEmailMap.put("suwoncatholic.ac.kr", "수원가톨릭대학교");
        schoolEmailMap.put("suwon.ac.kr", "수원대학교");
        schoolEmailMap.put("sookmyung.ac.kr", "숙명여자대학교");
        schoolEmailMap.put("kcc.ac.kr", "순복음총회신학교");
        schoolEmailMap.put("scnu.ac.kr", "순천대학교");
        schoolEmailMap.put("sch.ac.kr", "순천향대학교");
        schoolEmailMap.put("soongsil.ac.kr", "숭실대학교");
        schoolEmailMap.put("kcu.ac.kr", "숭실사이버대학교");
        schoolEmailMap.put("sgu.ac.kr", "신경대학교");
        schoolEmailMap.put("silla.ac.kr", "신라대학교");
        schoolEmailMap.put("acts.ac.kr", "아세아연합신학대학교");
        schoolEmailMap.put("ajou.ac.kr", "아주대학교");
        schoolEmailMap.put("anu.ac.kr", "안동대학교");
        schoolEmailMap.put("ayum.anyang.ac.kr", "안양대학교");
        schoolEmailMap.put("yonsei.ac.kr", "연세대학교");
        schoolEmailMap.put("ocu.ac.kr", "열린사이버대학교");
        schoolEmailMap.put("ynu.ac.kr", "영남대학교");
        schoolEmailMap.put("ytus.ac.kr", "영남신학대학교");
        schoolEmailMap.put("u1.ac.kr", "유원대학교");
        schoolEmailMap.put("ysu.ac.kr", "영산대학교");
        schoolEmailMap.put("youngsan.ac.kr", "영산선학대학교");
        schoolEmailMap.put("jesus.ac.kr", "예수대학교");
        schoolEmailMap.put("yewon.ac.kr", "예원예술대학교");
        schoolEmailMap.put("yiu.ac.kr", "용인대학교");
        schoolEmailMap.put("woosuk.ac.kr", "우석대학교");
        schoolEmailMap.put("wsu.ac.kr", "우송대학교");
        schoolEmailMap.put("unist.ac.kr", "UNIST");
        schoolEmailMap.put("ulsan.ac.kr", "울산대학교");
        schoolEmailMap.put("wonkwang.ac.kr", "원광대학교");
        schoolEmailMap.put("wdu.ac.kr", "원광디지털대학교");
        schoolEmailMap.put("uu.ac.kr", "위덕대학교");
        schoolEmailMap.put("eulji.ac.kr", "을지대학교");
        schoolEmailMap.put("ewhain.net", "이화여자대학교");
        schoolEmailMap.put("inje.ac.kr", "인제대학교");
        schoolEmailMap.put("iccu.ac.kr", "인천가톨릭대학교");
        schoolEmailMap.put("inu.ac.kr", "인천대학교");
        schoolEmailMap.put("inha.edu", "인하대학교");
        schoolEmailMap.put("pcts.ac.kr", "장로회신학대학교");
        schoolEmailMap.put("jnu.ac.kr", "전남대학교");
        schoolEmailMap.put("jbnu.ac.kr", "전북대학교");
        schoolEmailMap.put("jnue.kr", "전주교육대학교");
        schoolEmailMap.put("jj.ac.kr", "전주대학교");
        schoolEmailMap.put("jit.ac.kr", "정석대학");
        schoolEmailMap.put("jejue.ac.kr", "제주교육대학교");
        schoolEmailMap.put("jeju.ac.kr", "제주국제대학교");
        schoolEmailMap.put("jejunu.ac.kr", "제주대학교");
        schoolEmailMap.put("chosun.kr", "조선대학교");
        schoolEmailMap.put("jmail.ac.kr", "중부대학교");
        schoolEmailMap.put("cau.ac.kr", "중앙대학교");
        schoolEmailMap.put("sangha.ac.kr", "중앙승가대학교");
        schoolEmailMap.put("jwu.ac.kr", "중원대학교");
        schoolEmailMap.put("cue.ac.kr", "진주교육대학교");
        schoolEmailMap.put("cha.ac.kr", "차의과학대학교");
        schoolEmailMap.put("cs.ac.kr", "창신대학교");
        schoolEmailMap.put("changwon.ac.kr", "창원대학교");
        schoolEmailMap.put("chungwoon.ac.kr", "청운대학교");
        schoolEmailMap.put("cje.ac.kr", "청주교육대학교");
        schoolEmailMap.put("cju.ac.kr", "청주대학교");
        schoolEmailMap.put("chodang.ac.kr", "초당대학교");
        schoolEmailMap.put("chongshin.ac.kr", "총신대학교");
        schoolEmailMap.put("chugye.ac.kr", "추계예술대학교");
        schoolEmailMap.put("cnue.ac.kr", "춘천교육대학교");
        schoolEmailMap.put("cnu.ac.kr", "충남대학교");
        schoolEmailMap.put("chungbuk.ac.kr", "충북대학교");
        schoolEmailMap.put("kbtus.ac.kr", "침례신학대학교");
        schoolEmailMap.put("calvin.ac.kr", "칼빈대학교");
        schoolEmailMap.put("tnu.ac.kr", "탐라대학교");
        schoolEmailMap.put("ptu.ac.kr", "평택대학교");
        schoolEmailMap.put("postech.ac.kr", "POSTECH");
        schoolEmailMap.put("hknu.ac.kr", "한경대학교");
        schoolEmailMap.put("kaist.ac.kr", "KAIST");
        schoolEmailMap.put("knue.ac.kr", "한국교원대학교");
        schoolEmailMap.put("ut.ac.kr", "한국교통대학교");
        schoolEmailMap.put("iuk.ac.kr", "한국국제대학교");
        schoolEmailMap.put("koreatech.ac.kr", "한국기술교육대학교");
        schoolEmailMap.put("knou.ac.kr", "한국방송통신대학교");
        schoolEmailMap.put("kpu.ac.kr", "한국산업기술대학교");
        schoolEmailMap.put("bible.ac.kr", "한국성서대학교");
        schoolEmailMap.put("karts.ac.kr", "한국예술종합학교");
        schoolEmailMap.put("hufs.ac.kr", "한국외국어대학교");
        schoolEmailMap.put("nuch.ac.kr", "한국전통문화대학교");
        schoolEmailMap.put("knsu.ac.kr", "한국체육대학교");
        schoolEmailMap.put("kau.kr", "한국항공대학교");
        schoolEmailMap.put("kmou.ac.kr", "한국해양대학교");
        schoolEmailMap.put("hannam.ac.kr", "한남대학교");
        schoolEmailMap.put("handong.edu", "한동대학교");
        schoolEmailMap.put("halla.ac.kr", "한라대학교");
        schoolEmailMap.put("hanlyo.ac.kr", "한려대학교");
        schoolEmailMap.put("hallym.ac.kr", "한림대학교");
        schoolEmailMap.put("hanmin.ac.kr", "한민학교");
        schoolEmailMap.put("hanbat.ac.kr", "한밭대학교");
        schoolEmailMap.put("hanbuk.ac.kr", "한북대학교");
        schoolEmailMap.put("hanseo.ac.kr", "한서대학교");
        schoolEmailMap.put("hansung.ac.kr", "한성대학교");
        schoolEmailMap.put("uohs.ac.kr", "한세대학교");
        schoolEmailMap.put("hs.ac.kr", "한신대학교");
        schoolEmailMap.put("hanyang.ac.kr", "한양대학교");
        schoolEmailMap.put("hycu.ac.kr", "한양사이버대학교");
        schoolEmailMap.put("hytu.ac.kr", "한영신학대학교");
        schoolEmailMap.put("hanil.ac.kr", "한일장신대학교");
        schoolEmailMap.put("hanzhong.ac.kr", "한중대학교");
        schoolEmailMap.put("uhs.ac.kr", "협성대학교");
        schoolEmailMap.put("honam.ac.kr", "호남대학교");
        schoolEmailMap.put("htus.ac.kr", "호남신학대학교");
        schoolEmailMap.put("hoseo.edu", "호서대학교");
        schoolEmailMap.put("howon.ac.kr", "호원대학교");
        schoolEmailMap.put("hongik.ac.kr", "홍익대학교");
        schoolEmailMap.put("hscu.ac.kr", "화신사이버대학교");
    }









}
