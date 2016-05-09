//
//  StartLayer.cpp
//  SGCCC
//
//  Created by lequ on 14-5-4.
//
//

#include "StartLayer.h"

//#include "Test.h"

#include "SimpleAudioEngine.h"
#include "CCLuaEngine.h"
#include "lua_LQSocketBuffer.h"
#include "lua_ComService.h"
#include "lua_StartData.h"
#include "lua_LoadBattlefield.h"
#include "lua_LabelChange.h"
#include "lua_CoverView.h"
#include "WPlatform.h"
#include "lua_videoview_extends.h"
#include "lua_StatisticalManage.h"
#include "lua_GameVoice.h"
#include "lua_WPlatform.h"

#include "Fallenleaves.h"
#include "LQZipUtil.h"
#include "StatisticalManage.h"
#include "lua_module_register.h"
//#include "HclcData.h"

#include "../external/json/document.h"
#include "../external/json/writer.h"
#include "../external/json/stringbuffer.h"

#if (CC_TARGET_PLATFORM != CC_PLATFORM_WIN32)
#include <dirent.h>
//#include <sys stat.h="">
//#include <sys/types.h>
#include <sys/stat.h>
//#include <errno.h>
//#include <dirent.h>
#endif

// bds 下面这块是给分支预留的内容
///******************************************************************///
    #include "NDKHelper.h"
///******************************************************************///

using namespace CocosDenshion;
using namespace cocos2d;
using namespace network;

USING_NS_CC;

#define UKEY_USER "WJPLAYREUSERNAME"
#define UKEY_PASS "WJPLAYERPASSWORD"

#define UD UserDefault::getInstance() // UserDefault 实例


//"http://210.14.147.23:50200/gamepageweb/";
//"http://192.168.1.136:8080/gamepage/"  // 王凯戊
//"http://118.192.72.120:50200/gamepageweb/";
//"http://192.168.1.8:12222/gamepageweb/"


///** 自主平台 */
//lequ(0,"lq"),
///** 91IOS */
//ndios(1,"nd"),
///** pp */
//pp(2,"pp"),
///** 同步 */
//tongbu(3,"tb"),
///** 快用 */
//kuaiyong(4,"ky"),
///** AppStore */
//appStore(5,"as"),
///** iTools */
//iTools(6,"it"),
///** 91安卓 */
//ndAndroid(21,"ndandroid"),
///** Any */
//any(22,"any"),
///** 支付宝 */
//alipay(23,"ap"),
///** 多酷 */
//duoku(24,"dk"),
///** 棱镜 */
//lengjing(25,"lj"),
///**  360 */
//sdk360(26,"360"),
///**  安智 */
//wandoujia(27,"wdj"),
///** UC */
//uc(28,"uc"),
///**  当乐 */
//dangle(29,"dl"),
///**  小米 */
//xiaomi(30,"xm"),
///**  安智 */
//anzhi(31,"az"),
///**  和游戏 */
//and(32,"and"),
///** 优酷 */
//youku(33,"yk"),
///** 棱镜SDK */
//sdklj(34,"lj");
static const std::string LOGIN_URL =        "userlogin.do";
static const std::string REGISTER_URL =     "register.do";
static const std::string LOGIN2_URL =       "loginserver.do";
static const std::string SERVICELIST_URL =  "serverlist.do";
static const std::string GETANNOUNCE =      "getannounce.do";
static const std::string GETPAGE_URL =      "getServerPage.do";
static const std::string SENDPUSH =         "inputpushcode.do";

#pragma mark 貌似暂时废弃的数据类
static StartData *_startData = NULL;
StartData* StartData::getInstance()
{
    if(_startData == NULL){
        _startData = new StartData();
    }
    return _startData;
}

#pragma mark 登录页面生命周期相关
Scene* StartLayer::createScene()
{
    auto scene = Scene::create();
    auto layer = StartLayer::create();
//    layer->initLayer();
    scene->addChild(layer);
    return scene;
}

void StartLayer::onEnter()
{
    Layer::onEnter();
    initSdk();
}

void StartLayer::onExit()
{
    Layer::onExit();
}

bool StartLayer::init()
{
    if ( !Layer::init()){
        return false;
    }
    //    auto mregistered = MenuItemFont::create("测试", CC_CALLBACK_1(StartLayer::test, this));
    //    mregistered->setPosition(Point(120,120));
    //    auto menu = Menu::create(mregistered, NULL);
    //    menu->setPosition(Point::ZERO);
    //    this->addChild(menu,100);
    getPageServerDate.gameId = YOUXIHAO;
    return true;
}
// ccbi 绑定事件
bool StartLayer::initLayer()
{
    NodeLoaderLibrary *lib = NodeLoaderLibrary::newDefaultNodeLoaderLibrary();
    cocosbuilder::CCBReader *reader = new cocosbuilder::CCBReader(lib);
    lib->registerNodeLoader("StartLayer", LayerLoader::loader());
    Node *node = reader->readNodeGraphFromFile("files/ccbiui/login/Login.ccbi",this);
    reader->autorelease();
    
    Size visibleSize = Director::getInstance()->getVisibleSize();
    node->setAnchorPoint(Point(0.5, 0.5));
    node->ignoreAnchorPointForPosition(false);
    node->setPosition(Point(visibleSize.width / 2, visibleSize.height / 2));
    
    this->addChild(node);
    
    
    beginGame ->setString(WPlatform::getInstance()->getTipInfo(604413).c_str());
    TouchChange->setString(WPlatform::getInstance()->getTipInfo(604411).c_str());
    label_deng->setString(WPlatform::getInstance()->getTipInfo(604414).c_str());
    Label_yong->setString(WPlatform::getInstance()->getTipInfo(604415).c_str());
    label_mima->setString(WPlatform::getInstance()->getTipInfo(604425).c_str());
    
    LO_1->setString(WPlatform::getInstance()->getTipInfo(604426).c_str());
    Label_yong1->setString(WPlatform::getInstance()->getTipInfo(604415).c_str());
    label_mima1->setString(WPlatform::getInstance()->getTipInfo(604425).c_str());
    label_mima2->setString(WPlatform::getInstance()->getTipInfo(604427).c_str());
    Label_up->setString(WPlatform::getInstance()->getTipInfo(604410).c_str());
    showUserNameLabel->setString(WPlatform::getInstance()->getTipInfo(604409).c_str());
    severalNameLabel->setString(WPlatform::getInstance()->getTipInfo(604412).c_str());
    
    label_zhu2->setString(WPlatform::getInstance()->getTipInfo(604426).c_str());
    label_zhu1->setString(WPlatform::getInstance()->getTipInfo(604426).c_str());
    label_deng1->setString(WPlatform::getInstance()->getTipInfo(604414).c_str());
    
    label_yaoq->setString(WPlatform::getInstance()->getTipInfo(604546).c_str());
    Label_MyId->setString(WPlatform::getInstance()->getTipInfo(604543).c_str());
    label_readID->setString(WPlatform::getInstance()->getTipInfo(604544).c_str());
    yaoqing_id->setString("");
    yaoqing_des->setString(WPlatform::getInstance()->getTipInfo(604545).c_str());
    label_ok->setString(WPlatform::getInstance()->getTipInfo(508059).c_str());
    label_tui->setString(WPlatform::getInstance()->getTipInfo(604546).c_str());
    
    editPush = EditBox::create(Size(300, 45), Scale9Sprite::create());//创建EditBox
    editPush->setAnchorPoint(Point(0,0.5));
    editPush->setPosition(Point(430,349));
    editPush->setPlaceHolder(WPlatform::getInstance()->getTipInfo(604544).c_str());//设置editBox输入为空时的显示状态
    editPush->setInputMode(EditBox::InputMode::SINGLE_LINE);//输入模式，这里设置为数字
    editPush->setInputFlag(EditBox::InputFlag::SENSITIVE);
    editPush->setReturnType(EditBox::KeyboardReturnType::DONE);
    editPush->setDelegate(this);//开启委托
    //editBoxUserName->setFontColor(Color3B::RED);//设置文字颜色
    //editBox->setText("0");//设置默认显示数字
    this->loginFrindNode->addChild(editPush);
    
    return true;
}

void StartLayer::initCCBUI()
{
    StartData::getInstance()->deviceInfo = WPlatform::getInstance()->getDeviceInfo();
    
    LogoScene *logo = LogoScene::create();
    logo->setDelegate(this);
    logo->setLocalZOrder(999999999);
    this->addChild(logo);
}

void StartLayer::logoEnd()
{
    WPlatform::getInstance()->setUnLock();
    
    //先判断是否需要解压
    if(NEEDRELEASEZIP){//需要解压的情况下
        bool hasReleased =true;
        std::string nowZIPTAG = UserDefault::getInstance()->getStringForKey("ZIPRELEASETAG");
        if(nowZIPTAG.compare(UP_DATE_VER) != 0){
            hasReleased = false;
        }
        CCLOG("now ZIPVERTAG is %s",nowZIPTAG.c_str());
        CCLOG("write path is %s",FileUtils::getInstance()->getWritablePath().c_str());
        
        if(!hasReleased){//不存在的话就进行解压
            
            UpdateLayer *layer = UpdateLayer::create();
            layer->setshowType(2);//设置展示类别为解压
            this->addChild(layer);
            layer->starReleaseZIP();
            
            
            
        }else{
            CCLOG("已经解压过了");
        }
    }
    
    username = "";
    password = "";
    
    vectorServerlist.clear();
    
    //    Size visibleSize = Director::getInstance()->getVisibleSize();
    //    Point origin = Director::getInstance()->getVisibleOrigin();
    
    CCLOG("init StartLayer");
    
    const Size GAMESCENE = designResolutionSize;
    Size visibleSize =  Director::getInstance()->getVisibleSize();
    Sprite *loginBG = Sprite::create("res/background/jz_beijing.jpg");
    loginBG->setAnchorPoint(Point(0, 0));
    loginBG->setPosition(Point(visibleSize.width/2, visibleSize.height/2));
    loginBG->setPositionX(-(visibleSize.width- GAMESCENE.width)/2);
    loginBG->setPositionY(-(visibleSize.height-GAMESCENE.height)/2);
    Size bgSize = loginBG->getContentSize();
    float ofw = visibleSize.width / bgSize.width;
    float ofh = visibleSize.height / bgSize.height;
    float offsize = ofw > ofh ? ofw : ofh;
    //loginBG->setScaleX(visibleSize.width / GAMESCENE.width);
    //loginBG->setScaleY(visibleSize.height / GAMESCENE.height);
    loginBG->setScale(offsize);
    loginBGNode->addChild(loginBG);
    //    this->addChild(loginBG,-10);
    
    poLeft->setPositionX(-(visibleSize.width-GAMESCENE.width)/2);
    poLeft->setPositionY(-(visibleSize.height-GAMESCENE.height)/2);
    poRight->setPositionX( GAMESCENE.width + ((visibleSize.width-GAMESCENE.width)/2));
    poRight->setPositionY(-(visibleSize.height-GAMESCENE.height)/2);
    
    //labelInfo = Label::createWithSystemFont("..测试信息", "", 15);
    //labelInfo->setAnchorPoint(Point::ZERO);
    //labelInfo->setPosition(Point(0, 0));
    //this->addChild(labelInfo);
    //labelInfo->enableShadow(Color4B::ORANGE);
    
    editBoxUserName = EditBox::create(Size(290, 35.0), Scale9Sprite::create());//创建EditBox，第一个参数设置输入框的大小，第二个参数和和“九妹”相关
    editBoxUserName->setAnchorPoint(Point(0,0.5));
    editBoxUserName->setPosition(Point(435,403));
    editBoxUserName->setPlaceHolder(WPlatform::getInstance()->getTipInfo(604362).c_str());//设置editBox输入为空时的显示状态
    editBoxUserName->setInputMode(EditBox::InputMode::SINGLE_LINE);//输入模式，这里设置为数字
    editBoxUserName->setInputFlag(EditBox::InputFlag::SENSITIVE);
    editBoxUserName->setReturnType(EditBox::KeyboardReturnType::DONE);
    editBoxUserName->setDelegate(this);//开启委托
    //editBoxUserName->setFontColor(Color3B::RED);//设置文字颜色
    //editBox->setText("0");//设置默认显示数字
    this->loginNode->addChild(editBoxUserName);
    
    editBoxPassWord = EditBox::create(Size(290, 35.0), Scale9Sprite::create());//创建EditBox，第一个参数设置输入框的大小，第二个参数和和“九妹”相关
    editBoxPassWord->setInputFlag(EditBox::InputFlag::PASSWORD);
    editBoxPassWord->setAnchorPoint(Point(0,0.5));
    editBoxPassWord->setPosition(Point(435,351));
    editBoxPassWord->setPlaceHolder(WPlatform::getInstance()->getTipInfo(604363).c_str());//设置editBox输入为空时的显示状态
    editBoxPassWord->setInputMode(EditBox::InputMode::ANY);//输入模式，这里设置为数字
    editBoxPassWord->setDelegate(this);//开启委托
    //editBoxPassWord->setFontColor(Color3B::RED);//设置文字颜色
    //editBox->setText("0");//设置默认显示数字
    this->loginNode->addChild(editBoxPassWord);
    
    // 注册的按钮
    editBoxZCUserName = EditBox::create(Size(290, 35.0), Scale9Sprite::create());//创建EditBox，第一个参数设置输入框的大小，第二个参数和和“九妹”相关
    editBoxZCUserName->setMaxLength(16);
    editBoxZCUserName->setAnchorPoint(Point(0,0.5));
    editBoxZCUserName->setPosition(Point(435,407));
    editBoxZCUserName->setPlaceHolder(WPlatform::getInstance()->getTipInfo(604362).c_str());//设置editBox输入为空时的显示状态
    editBoxZCUserName->setInputFlag(EditBox::InputFlag::SENSITIVE);
    editBoxZCUserName->setReturnType(EditBox::KeyboardReturnType::DONE);
    editBoxZCUserName->setInputMode(EditBox::InputMode::ANY);//输入模式，这里设置为数字
    editBoxZCUserName->setDelegate(this);//开启委托
    //editBoxZCUserName->setFontColor(Color3B::RED);//设置文字颜色
    //editBox->setText("0");//设置默认显示数字
    this->registeredNode->addChild(editBoxZCUserName);
    
    editBoxZCPassWord = EditBox::create(Size(290, 35.0), Scale9Sprite::create());//创建EditBox，第一个参数设置输入框的大小，第二个参数和和“九妹”相关
    editBoxZCPassWord->setInputFlag(EditBox::InputFlag::PASSWORD);
    editBoxZCPassWord->setAnchorPoint(Point(0,0.5));
    editBoxZCPassWord->setPosition(Point(435,356));
    editBoxZCPassWord->setPlaceHolder(WPlatform::getInstance()->getTipInfo(604363).c_str());//设置editBox输入为空时的显示状态
    editBoxZCPassWord->setInputMode(EditBox::InputMode::ANY);//输入模式，这里设置为数字
    editBoxZCPassWord->setDelegate(this);//开启委托
    //editBoxZCPassWord->setFontColor(Color3B::RED);//设置文字颜色
    //editBox->setText("0");//设置默认显示数字
    this->registeredNode->addChild(editBoxZCPassWord);
    
    editBoxZCPassWord2 = EditBox::create(Size(290, 35.0), Scale9Sprite::create());//创建EditBox，第一个参数设置输入框的大小，第二个参数和和“九妹”相关
    editBoxZCPassWord2->setInputFlag(EditBox::InputFlag::PASSWORD);
    editBoxZCPassWord2->setAnchorPoint(Point(0,0.5));
    editBoxZCPassWord2->setPosition(Point(435,304));
    editBoxZCPassWord2->setPlaceHolder(WPlatform::getInstance()->getTipInfo(604363).c_str());//设置editBox输入为空时的显示状态
    editBoxZCPassWord2->setInputMode(EditBox::InputMode::ANY);//输入模式，这里设置为数字
    editBoxZCPassWord2->setDelegate(this);//开启委托
    //editBoxZCPassWord2->setFontColor(Color3B::RED);//设置文字颜色
    //editBox->setText("0");//设置默认显示数字
    this->registeredNode->addChild(editBoxZCPassWord2);
    
    this->promptInfoLabel->setString(WPlatform::getInstance()->getTipInfo(604474).c_str()); // 604474
        
    //    auto closeItem = MenuItemFont::create("登录",
    //                                          CC_CALLBACK_1(StartLayer::menuLoginCallback, this));
    //	closeItem->setPosition(Point(origin.x + visibleSize.width - closeItem->getContentSize().width/2 -100,origin.y + closeItem->getContentSize().height/2+100));
    //
    //    auto mregistered = MenuItemFont::create("注册", CC_CALLBACK_1(StartLayer::menuRegistered, this));
    //    mregistered->setPosition(Point(120,120));
    //
    //    auto menu = Menu::create(closeItem,mregistered, NULL);
    //    menu->setPosition(Point::ZERO);
    //    this->addChild(menu);
    
    logoSP->setScale(0.6, 0.6);
    logoSP->setPosition(Point(logoSP->getPositionX()-70,logoSP->getPositionY()));
    
    username = UserDefault::getInstance()->getStringForKey(UKEY_USER);
    password = UserDefault::getInstance()->getStringForKey(UKEY_PASS);
    
    //    sendAnnouncement(); // 发送公告请求
    
    if(landingTypes == 0){
        setVisibleUserQQ(false);
    }else if(landingTypes == 1){
        this->loginAutoNode->setVisible(false);
        this->loginNode->setVisible(false);
        this->registeredNode->setVisible(false);
        
        setVisibleUserQQ(true);
        setVisibleUserName(false);
    }
    
    if(isSelfLanding){
        if(username.compare("") != 0 && password.compare("") != 0){
            editBoxUserName->setText(username.c_str());
            editBoxPassWord->setText(password.c_str());
            
            this->loginAutoNode->setVisible(true);
            
            this->showUserNameLabel->setString(username.c_str());
            
            // 有账号密码了 直接登录去拿服务器列表
            DialogWaiting::getInstance()->OnShow(WPlatform::getInstance()->getTipInfo(604364));
            
            sendValidation();
        }else{
            this->loginAutoNode->setVisible(false);
            this->loginNode->setVisible(true);
            this->registeredNode->setVisible(false);
        }
    }else{
        // bds 分支添加的第三方登录
        ///******************************************************************///
        this->loginAutoNode->setVisible(true);
//        这个宏看需要是否添加
//#if CC_TARGET_PLATFORM != CC_PLATFORM_IOS
        this->login();
//#endif
        /// end ******************************************************************///
        
    }
    
    int isSound = UserDefault::getInstance()->getIntegerForKey("UD_MUSICBG_KEY");
    if (isSound == 0) {
        SimpleAudioEngine::getInstance()->playBackgroundMusic("res/audio/mainmusic.mp3",true);
    } else {
        SimpleAudioEngine::getInstance()->stopBackgroundMusic();
    }
    
    //    Fallenleaves* rt = Fallenleaves::create();
    //    this->addChild(rt);
    
    //    std::string str = HclcData::getInstance()->getLuaVarOneOfTable("tableRobFragmentsMount","pid");
    //    CCLOG("str:%s",str.c_str());
    
    
    /*
     auto size = Director::getInstance()->getWinSize();
     ClippingNode *clip = ClippingNode::create();
     Sprite *gameTitle = this->imgLogo;//Sprite::create("res/figure/card-cloth-20.png");
     //    clip->setInverted(true);
     clip->setAlphaThreshold(0);
     clip->setStencil(gameTitle);
     clip->setContentSize(Size(gameTitle->getContentSize().width,
     gameTitle->getContentSize().height));
     Size clipSize = clip->getContentSize();
     clip->setPosition(Point(size.width / 2 - 370, size.height / 2));
     
     Sprite *spark = Sprite::create("res/point.png");
     spark->setScale(2, 20);
     spark->setRotation(-30);
     clip->addChild(gameTitle);
     spark->setPosition(-size.width / 2,0);
     clip->addChild(spark);//会被裁减
     this->addChild(clip);
     
     auto moveAction = MoveTo::create(0.8, Point(clipSize.width, 0));
     auto moveBackAction = MoveTo::create(0.0, Point(-clipSize.width, 0));
     auto delay = DelayTime::create(2);
     auto seq = Sequence::create(moveAction, moveBackAction, delay, NULL);
     auto repeatAction = RepeatForever::create(seq);
     spark->runAction(repeatAction);//进行左右移动的重复动作
     */
    
    std::string resVer = UD->getStringForKey(RESDOWNVERKEYASSETS).c_str();
    if(resVer.compare("") == 0){ resVer = UP_DATE_VER;}
    //std::string ver = StringUtils::format("v%s %s",LQVERSION.c_str(),resVer.c_str());
    std::string ver = StringUtils::format("v%s",resVer.c_str());
    Label *verLabel = Label::createWithSystemFont(ver.c_str(), "", 24);
    verLabel->setAnchorPoint(Point(0,0));
    verLabel->setPosition(Point(5,5));
    verLabel->setTag(12398498);
    this->addChild(verLabel);
    
    CCLOG("Verect:%s",UP_DATE_VER.c_str());
}


SEL_MenuHandler StartLayer::onResolveCCBCCMenuItemSelector(Ref * pTarget, const char * pSelectorName) {
    return NULL;
}
Control::Handler StartLayer::onResolveCCBCCControlSelector(Ref * pTarget, const char * pSelectorName) {
    log("MainScene::按钮绑定");
    
    CCB_SELECTORRESOLVER_CCCONTROL_GLUE(this, "OnBtnClickLoginCloseTui", StartLayer::OnBtnClickLoginCloseTui);
    CCB_SELECTORRESOLVER_CCCONTROL_GLUE(this, "OnPlayBtnClickedOk", StartLayer::OnPlayBtnClickedOk);
    CCB_SELECTORRESOLVER_CCCONTROL_GLUE(this, "OnPlayBtnClickedTui", StartLayer::OnPlayBtnClickedTui);
    
    CCB_SELECTORRESOLVER_CCCONTROL_GLUE(this, "OnPlayBtnClicked", StartLayer::OnPlayBtnClicked);
    CCB_SELECTORRESOLVER_CCCONTROL_GLUE(this, "OnBtnLoginOrRegistered", StartLayer::OnBtnLoginOrRegistered);
    CCB_SELECTORRESOLVER_CCCONTROL_GLUE(this, "OnBtnClickLoginClose", StartLayer::OnBtnClickLoginClose);
    CCB_SELECTORRESOLVER_CCCONTROL_GLUE(this, "OnBtnZCYHClick", StartLayer::OnBtnZCYHClick);
    CCB_SELECTORRESOLVER_CCCONTROL_GLUE(this, "OnBtnClickZCClose", StartLayer::OnBtnClickZCClose);
    CCB_SELECTORRESOLVER_CCCONTROL_GLUE(this, "OnBtnRegisteredClick", StartLayer::OnBtnRegisteredClick);
    CCB_SELECTORRESOLVER_CCCONTROL_GLUE(this, "OnBtnClickSList", StartLayer::OnBtnClickSList);
    CCB_SELECTORRESOLVER_CCCONTROL_GLUE(this, "OnBtnUpClick", StartLayer::OnBtnUpClick);
    CCB_SELECTORRESOLVER_CCCONTROL_GLUE(this, "OnClickWChar", StartLayer::OnClickWChar);
    CCB_SELECTORRESOLVER_CCCONTROL_GLUE(this, "OnClickQQ", StartLayer::OnClickQQ);

    return NULL;
}
bool StartLayer::onAssignCCBMemberVariable(Ref * pTarget, const char * pMemberVariableName, Node * pNode) {
    log("绑定 node === %s",pMemberVariableName);
    
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "label_yaoq", Label *, label_yaoq);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "Label_MyId", Label *, Label_MyId);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "label_readID", Label *, label_readID);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "yaoqing_id", Label *, yaoqing_id);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "yaoqing_des", Label *, yaoqing_des);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "label_ok", Label *, label_ok);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "label_tui", Label *, label_tui);
    
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "beginGame", Label *, beginGame);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "TouchChange", Label *, TouchChange);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "Label_yong", Label *, Label_yong);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "label_mima", Label *, label_mima);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "Label_up", Label *, Label_up);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "LO_1", Label *, LO_1);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "Label_yong1", Label *, Label_yong1);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "label_mima1", Label *, label_mima1);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "label_mima2", Label *, label_mima2);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "label_deng", Label *, label_deng);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "label_zhu2", Label *, label_zhu2);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "label_deng1", Label *, label_deng1);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "label_zhu1", Label *, label_zhu1);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "promptInfoLabel", Label *, promptInfoLabel);
    
    
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "loginFrindNode", Layer *, loginFrindNode);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "tuiguangNode", Layer *, tuiguangNode);
    
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "loginAutoNode", Layer *, loginAutoNode);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "showUserNameLabel", Label *, showUserNameLabel);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "severalNameLabel", Label *, severalNameLabel);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "verLabel", Label *, verLabel);
    //CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "loginBG", Sprite *, loginBG);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "loginBGNode", Layer *, loginBGNode);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "usernameInputLayer", Layer *, usernameInputLayer);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "qqInputLayer", Layer *, qqInputLayer);    
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "logoSP", Sprite *, logoSP);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "stRe", Sprite *, stRe);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "stYu", Sprite *, stYu);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "stXin", Sprite *, stXin);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "stWei", Sprite *, stWei);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "stLiu", Sprite *, stLiu);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "stTui", Sprite *, stTui);

    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "poLeft", Sprite *, poLeft);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "poRight", Sprite *, poRight);
    
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "loginNode", Layer *, loginNode);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "udNode", Layer *, udNode);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "registeredNode", Layer *, registeredNode);
    CCB_MEMBERVARIABLEASSIGNER_GLUE(this, "dialogUDLayer", Layer *, dialogUDLayer);
   
    return true;
}

void StartLayer::onNodeLoaded(cocos2d::Node * node, cocosbuilder::NodeLoader * nodeLoader)
{
}


#pragma mark 按钮事件
#pragma mark 点击进入游戏按钮,准备登录游戏了
void StartLayer::OnPlayBtnClicked(cocos2d::Ref * sender, cocos2d::extension::Control::EventType pControlEvent) // 进入游戏
{
    
//    ParticleSystemQuad *particle1 = ParticleSystemQuad::create("res/particle/QHStar.plist");
//    particle1->setAnchorPoint(Point(0.5,0.5));
//    particle1->setPosition(Point(500, 300));
//    this->addChild(particle1);
//    if(1==1) {
//        return;
//    }
    
    /**
    
    ///onClickDialogBtn
    if(1==1) {
        return;
    }
     */
    
    CCLOG( "进入游戏");
    
    Control * control = (Control *)sender;
    int tag = control->getTag();
    
    if(username.compare("")==0 || password.compare("")==0){
        //labelInfo->setString("请输入账号和密码");
        //DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604365));
        OnBtnLoginOrRegistered(sender,pControlEvent);
        return;
    }
    
    if(tag == 3){
        username = editBoxUserName->getText();
        password = editBoxPassWord->getText();
        sendValidation();
        return;
    }
    
    if(vectorServerlist.size() == 0){
        //DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604366));
        OnBtnLoginOrRegistered(sender,pControlEvent);
        return;
    }
    
    if(stYu->isVisible()) {
        for (int i = 0; i<vectorServerlist.size(); i++) {
            std::string lsid = vectorServerlist[i].sid;
            if(this->sid.compare(lsid) == 0){
                DialogPrompt::create()->OnShow(vectorServerlist[i].desc);
                return;
            }
        }
        DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604356));
        return;
    }
    
    if(stWei->isVisible()) {
        DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604356));
        return;
    }
    
    // 还没做处理  mack一下
    // 自动登录验证如果失败了 也没处理  在mack一下
    bool isSid = this->sid.compare("")== 0; // 如果sid没有那么就从头登录
    if(!isSid){
        //  可以直接登录游戏
        
        sendReSeverIP();
        DialogWaiting::getInstance()->OnShow(WPlatform::getInstance()->getTipInfo(604367));
    }else{
        //  可能自动验证完成后Y又重新输入账号密码了
        //  也可能在自动请求服务器列表之前的操作出问题了
        //  反正这里是要重新登录验证的
        sendValidation();
        DialogWaiting::getInstance()->OnShow(WPlatform::getInstance()->getTipInfo(604367));
    }
    
}

void StartLayer::OnBtnLoginOrRegistered(cocos2d::Ref * sender, cocos2d::extension::Control::EventType pControlEvent) // 点击登录或者注册按钮
{
    if(isSelfLanding) {
        username = UserDefault::getInstance()->getStringForKey(UKEY_USER);
        password = UserDefault::getInstance()->getStringForKey(UKEY_PASS);
        CCLOG("usernmae = %s  password = %s",username.c_str(),password.c_str());
        if(username.compare("") != 0 && password.compare("") != 0){
            editBoxUserName->setText(username.c_str());
            editBoxPassWord->setText(password.c_str());
        }
        
        this->loginAutoNode->setVisible(false);
        this->loginNode->setVisible(true);
    } else {
        // bds 分支添加的第三方登录
        ///******************************************************************///
        this->login();
    }
}

void StartLayer::OnPlayBtnClickedTui(cocos2d::Ref * sender, cocos2d::extension::Control::EventType pControlEvent) // 点击登录或者注册按钮
{
    if (pushCode != "") {
        this->yaoqing_id->setString(pushCode);
        this->loginFrindNode->setVisible(true);
        this->loginAutoNode->setVisible(false);
    }
    else
    {
        DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(602470));
    }
}

void StartLayer::OnPlayBtnClickedOk(cocos2d::Ref * sender, cocos2d::extension::Control::EventType pControlEvent) // 点击登录或者注册按钮
{
    if (editPush->getText() != "") {
        std::string url = IP_URL + SENDPUSH; ;
        rapidjson::Document fromScratch;
        fromScratch.SetObject();
        rapidjson::Document::AllocatorType& allocator = fromScratch.GetAllocator();
        fromScratch.AddMember("type", rapidjson::StringRef(getPageServerDate.type.c_str()), allocator); // 平台相关
        fromScratch.AddMember("fid", rapidjson::StringRef(getPageServerDate.fid.c_str()), allocator);
        fromScratch.AddMember("userId", rapidjson::StringRef(getPageServerDate.userId.c_str()), allocator);
        fromScratch.AddMember("gameId", YOUXIHAO, allocator);
        //fromScratch.AddMember("page", getPageServerDate.page, allocator);
        fromScratch.AddMember("userType", USERTYPE, allocator);// 内部0,这个始终传0，如果有特殊需要修改的，直接通知服务端改数据库
        fromScratch.AddMember("inputPushCode", rapidjson::StringRef(editPush->getText()), allocator);// build code 数字的
        rapidjson::StringBuffer strbuf;
        rapidjson::Writer<rapidjson::StringBuffer> writer(strbuf);
        fromScratch.Accept(writer);
        printf("请求列表(2)--\n%s\n--\n", strbuf.GetString());
        sendHTTP(url, strbuf.GetString(), callfuncND_selector(StartLayer::onPushServerCallBack));
    }
}

void StartLayer::onPushServerCallBack(Node *sender, void *data)
{
    CCLOG("收到推广码数据");
    cocos2d::network::HttpResponse *response = (cocos2d::network::HttpResponse*)data;
    if(response->getResponseCode() == 200){
        std::vector<serverListType> serverlistData;
        std::vector<char> *buffer = response->getResponseData();
        std::string bufs(buffer->begin(),buffer->end());
        CCLOG("收到推广码数据:%s",bufs.c_str());
        rapidjson::Document d1;
        d1.Parse<0>(bufs.c_str());
        const rapidjson::Value &data = d1["suc"];
        bool issuccess = data.GetBool();
       
        if (issuccess) {
            DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604001));
        }
        else
        {
            const rapidjson::Value &tip = d1["tipId"];
            DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(tip.GetInt()));
        }
        this->loginFrindNode->setVisible(false);
        this->loginAutoNode->setVisible(true);
    }
}

void StartLayer::OnBtnClickLoginCloseTui(cocos2d::Ref * sender, cocos2d::extension::Control::EventType pControlEvent)
{
    this->loginFrindNode->setVisible(false);
    this->loginAutoNode->setVisible(true);
}


void StartLayer::OnBtnClickLoginClose(cocos2d::Ref * sender, cocos2d::extension::Control::EventType pControlEvent){
    this->loginAutoNode->setVisible(true);
    this->loginNode->setVisible(false);
}

void StartLayer::OnBtnZCYHClick(cocos2d::Ref * sender, cocos2d::extension::Control::EventType pControlEvent)
{
    this->registeredNode->setVisible(true);
    this->loginNode->setVisible(false);
}

void StartLayer::OnBtnClickZCClose(cocos2d::Ref * sender, cocos2d::extension::Control::EventType pControlEvent)
{
    this->registeredNode->setVisible(false);
    this->loginNode->setVisible(true);
}
#pragma mark 发送自由帐号的注册请求
void StartLayer::OnBtnRegisteredClick(cocos2d::Ref * sender, cocos2d::extension::Control::EventType pControlEvent) // 注册界面的注册按钮
{
    std::string zcUser = editBoxZCUserName->getText(); // 注册时候输入的用户名
    std::string zcPass = editBoxZCPassWord->getText(); // 注册时候输入的密码
    std::string zcPass2 = editBoxZCPassWord2->getText(); // 注册时候输入的密码
    if(zcPass.compare(zcPass2)!=0){
        //labelInfo->setString("两次输入的密码不一致");
        DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604368));
        return;
    }
    if(zcUser.compare("")==0 || zcPass.compare("")==0){
        //labelInfo->setString("用户名或密码不能为空");
        DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604369));
        return;
    }
    
    username = zcUser;
    password = zcPass;
    
    rapidjson::Document fromScratch;
    fromScratch.SetObject();
    rapidjson::Document::AllocatorType& allocator = fromScratch.GetAllocator();
    fromScratch.AddMember("userId", rapidjson::StringRef(username.c_str()), allocator);
    fromScratch.AddMember("authorization", rapidjson::StringRef(password.c_str()), allocator);
    
    rapidjson::StringBuffer strbuf;
	rapidjson::Writer<rapidjson::StringBuffer> writer(strbuf);
	fromScratch.Accept(writer);
    printf("注册--\n%s\n--\n", strbuf.GetString());
    
    // 注册
    std::string url = IP_URL + REGISTER_URL;
    sendHTTP(url, strbuf.GetString(), callfuncND_selector(StartLayer::onLoginRequestCompleted));
    pushCode = "";
    //labelInfo->setString("发送注册请求..");
    DialogWaiting::getInstance()->OnShow(WPlatform::getInstance()->getTipInfo(604370));

}

void StartLayer::OnBtnClickSList(cocos2d::Ref * sender, cocos2d::extension::Control::EventType pControlEvent)
{
    if(this->vectorPageList.size() == 0)
    {
        return;
    }
    this->loginAutoNode->setVisible(false);
    this->registeredNode->setVisible(false);
    this->loginNode->setVisible(false);
    
    
    this->onShowServerList(NULL);
}

void StartLayer::OnBtnUpClick(cocos2d::Ref * sender, cocos2d::extension::Control::EventType pControlEvent)
{
    // 更下砸最新的客户端去
    WPlatform::getInstance()->openUrl(updateClientURL.c_str());
    exit(0);
}

void StartLayer::OnClickWChar(cocos2d::Ref * sender, cocos2d::extension::Control::EventType pControlEvent)
{
    CCLOG("微信");
	
// bds 下面这块是给分支预留的内容
///******************************************************************///
    NDKHelper::AddSelector("login", "loginCallBack", callfuncND_selector(StartLayer::loginCallBack),this);
    __Dictionary* param = __Dictionary::create();
    param->setObject(__String::create("wechat"), "logintype");
    SendMessageWithParams("login", param);
///******************************************************************///
}
void StartLayer::OnClickQQ(cocos2d::Ref * sender, cocos2d::extension::Control::EventType pControlEvent)
{
    CCLOG("QQ");
    //setVisibleQQLandOK();
	
// bds 下面这块是给分支预留的内容
///******************************************************************///
    NDKHelper::AddSelector("login", "loginCallBack", callfuncND_selector(StartLayer::loginCallBack),this);
    __Dictionary* param = __Dictionary::create();
    param->setObject(__String::create("qq"), "logintype");
    SendMessageWithParams("login", param);
///******************************************************************///
}

void StartLayer::onClickDialogBtn(Dialog *dialog,int btnIndex ,int fid)
{
    if(btnIndex == 0) { // 取消
        //        dialog->OnClose();
        WPlatform::getInstance()->openUrl(getActivateCodeURL.c_str());
    }else if(btnIndex == 1){ // 确定
        // dialog->OnClose();
        
        rapidjson::Document fromScratch;
        fromScratch.SetObject();
        rapidjson::Document::AllocatorType& allocator = fromScratch.GetAllocator();
        fromScratch.AddMember("userID", rapidjson::StringRef(StartData::getInstance()->uid.c_str()), allocator);
        fromScratch.AddMember("type", rapidjson::StringRef(PINGTAN.c_str()), allocator);
        fromScratch.AddMember("code", rapidjson::StringRef(editActivate->getText()), allocator);
        
        rapidjson::StringBuffer strbuf;
        rapidjson::Writer<rapidjson::StringBuffer> writer(strbuf);
        fromScratch.Accept(writer);
        printf("获取服务器IP地址--\n%s\n--\n", strbuf.GetString());
        
        //std::string sdf = "{\"uid\":\"lq.qazwsxedc\",\"sid\":\"100\"}";
        std::string url = IP_URL + "activecode.do";
        sendHTTP(url, strbuf.GetString(), callfuncND_selector(StartLayer::onActivate));
        pushCode = "";
        DialogWaiting::getInstance()->OnShow(WPlatform::getInstance()->getTipInfo(604371));
    }
}



//void StartLayer::initUDUI()
//{
//    // 设置缓存启用
//    std::vector<std::string> searchPaths = CCFileUtils::getInstance()->getSearchPaths();
//    std::vector<std::string>::iterator iter = searchPaths.begin();
//    searchPaths.insert(iter, CCFileUtils::getInstance()->getWritablePath());
//    CCFileUtils::getInstance()->setSearchPaths(searchPaths);
//    
//    Size visibleSize = designResolutionSize;//Director::getInstance()->getVisibleSize();
//    
//    Sprite *pSpriteBG = Sprite::create("res/update/up_progressBG.png");
//    pSpriteBG->setAnchorPoint(Point(0.5, 0));
//    pSpriteBG->setPosition(Point(visibleSize.width/2,0));
//    this->udNode->addChild(pSpriteBG);
//    Sprite *pline = Sprite::create("res/update/up_progress.png");
//    //int lineWidth = pline->boundingBox().size.width;
//    progress = ProgressTimer::create(pline);
//    progress->setBarChangeRate(Point(1, 0));    //设置进度条的长度和高度开始变化的大小
//    progress->setMidpoint(Point(0, 0));
//    progress->setType(ProgressTimer::Type::BAR);    //设置进度条为水平
//    progress->setPercentage(0.0f);    //设置初始百分比的值
//    progress->setPosition(Point(pSpriteBG->getContentSize().width/2,
//                                pSpriteBG->getContentSize().height/2));    //放置进度条位置
//    pSpriteBG->addChild(progress);    //加入Layer中
//    
//    
//    // 添加显示下载信息的label
//    labelDownloadInfo = Label::createWithTTF("",TTF_YOUYUAN, 30);
//    labelDownloadInfo->setPosition(Point(visibleSize.width/2, 100));
//    labelDownloadInfo->setAnchorPoint(Point(0.5, 0.5));
//    labelDownloadInfo->setAlignment(TextHAlignment::CENTER);
//    this->udNode->addChild(labelDownloadInfo);
//    
//    //  显示资源的版本号 当前版本和服务器版本
//    labelVersion = Label::createWithTTF("",TTF_YOUYUAN, 20);
//    labelVersion->setPosition(Point(visibleSize.width-5,visibleSize.height-5));
//    labelVersion->setAnchorPoint(Point(1,1));
//    this->udNode->addChild(labelVersion);
//    
//    // 启动下载
////    UpdateRes *update = UpdateRes::getInstance();
////    update->setDelegate(this);
////    update->starUpdate();
//}

//// 显示更细资源的版本好
//void StartLayer::pushSVersion(std::string currentVer,std::string targetVer)
//{
//    std::string sv=StringUtils::format("当前版本:%s",currentVer.c_str());
//    std::string tv="";
//    if (targetVer.compare("") != 0) {
//        tv = StringUtils::format("最新版本:%s",targetVer.c_str());
//    }
//    labelVersion->setString(StringUtils::format("%s\n%s",sv.c_str(),tv.c_str()));
//}
//// 显示现在的异常信息
//void StartLayer::errProcess(int errCode, std::string errInfo)
//{
//    if (errCode == errCode_getMainJson) {
//        labelDownloadInfo->setString("请求更新文件列表失败");
//    }else if(errCode == errCode_uploadErr) {
//        labelDownloadInfo->setString("更新失败...");
//    }else if(errCode == errCode_upversion) {
//        labelDownloadInfo->setString("需要更新客户端了");
//    }
//}
//
//// UpdateRes 获得下载进度用来显示用
//void StartLayer::curentResProcess(int currentNumber,int totalNumber, unsigned long currentBytes, unsigned long totalBytes)
//{
//    labelDownloadInfo->setString(StringUtils::format("(%lu/%lu)\n%d/%d",currentBytes,totalBytes,currentNumber,totalNumber));
//    
//    float value = float(currentBytes)/float(totalBytes);
//    progress->setPercentage((value)*100);
//}
//
//// 更新完成了
//void StartLayer::updateResEnd()
//{
//    labelDownloadInfo->setString("文件更新完成");
//    
//    this->initCCBUI();
//    this->udNode->setVisible(false);
//    
//    if(username.compare("") != 0 && password.compare("") != 0){
//        this->loginAutoNode->setVisible(true);
//        this->loginNode->setVisible(false);
//        this->registeredNode->setVisible(false);
//    }else{
//        this->loginAutoNode->setVisible(false);
//        this->loginNode->setVisible(true);
//        this->registeredNode->setVisible(false);
//    }
//}


#pragma mark 激活码
void StartLayer::showActivate()
{
    dialogActivate = Dialog::create();
    dialogActivate->OnShow(this, 1, "");
    dialogActivate->setBtnName(WPlatform::getInstance()->getTipInfo(604372), "确定");
    dialogActivate->setBG("");
    
    Scale9Sprite *s9sp = Scale9Sprite::createWithSpriteFrameName("ty_wenzidi.png");
    s9sp->setPreferredSize(Size(250,35));
    editActivate= EditBox::create(Size(250, 35.0), s9sp);//创建EditBox，第一个参数设置输入框的大小，第二个参数和和“九妹”相关
    editActivate->setAnchorPoint(Point(0.5,0.5));
    editActivate->setPosition(Point(dialogActivate->tbg->getContentSize().width/2,dialogActivate->tbg->getContentSize().height/2 + 30));
    editActivate->setPlaceHolder("请输入激活码");//设置editBox输入为空时的显示状态
    editActivate->setInputMode(EditBox::InputMode::ANY);//输入模式，这里设置为数字
    editActivate->setDelegate(this);//开启委托
    dialogActivate->tbg->addChild(editActivate);
}


#pragma mark 激活码结果回调
void StartLayer::onActivate(Node *sender, void *data)
{
    HttpResponse *response = (HttpResponse*)data;
    if(response->getResponseCode() == 200){
        std::vector<char> *buffer = response->getResponseData();
        std::string bufs(buffer->begin(),buffer->end());
        CCLOG("收到登录到游戏服务器数据:%s",bufs.c_str());
        
        rapidjson::Document d1;
        d1.Parse<0>(bufs.c_str());
        int code = d1["code"].GetInt();
        std::string desc = d1["desc"].GetString();
        
        if(code == 0) {
            // 失败了
            if(desc.compare("601200") == 0) {
                DialogPrompt::create()->OnShow("平台类型不存在");
            }else if(desc.compare("601201") == 0) {
                DialogPrompt::create()->OnShow("激活码不存在");
            }else if(desc.compare("601202") == 0) {
                DialogPrompt::create()->OnShow("激活码已被使用");
            }else if(desc.compare("601203") == 0) {
                DialogPrompt::create()->OnShow("请输入激活码");
            } else {
                DialogPrompt::create()->OnShow("未知错误");
            }
        }else{
            // 成功了
            dialogActivate->OnClose();
            DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604373));
        }
        
    }else{
        String *str = String::createWithFormat(WPlatform::getInstance()->getTipInfo(604374).c_str(),response->getResponseCode());
        //labelInfo->setString(str->getCString());
        DialogPrompt::create()->OnShow(str->getCString());
    }
    
    DialogWaiting::getInstance()->OnClose();
}

void StartLayer::test(cocos2d::Ref* pSender)
{
    //    loginNode->release();
    //    registeredNode->release();
    //    loginAutoNode->release();
    //    showUserNameLabel->release();
    //    severalNameLabel->release();
    //
    //    Scene *t = Test::createScene();
    //    Director::getInstance()->replaceScene(t);
    //
    ////    this->removeFromParentAndCleanup(true);
    ////    Director::getInstance()->getTextureCache()->removeUnusedTextures();
    ////    SpriteFrameCache::getInstance()->removeUnusedSpriteFrames();
    //
    //
    //    Director::getInstance()->getTextureCache()->removeAllTextures();
    //    SpriteFrameCache::getInstance()->removeUnusedSpriteFrames();
    
}
#pragma mark HTTP工具方法
// 发送请求
void StartLayer::sendHTTP(std::string url,std::string postContent,SEL_CallFuncND  callBack){
    HttpRequest* request = new HttpRequest();
    request->setUrl(url.c_str());
    CCLOG("url.c_str() = %s",url.c_str());
    CCLOG("send data = %s",postContent.c_str());
    request->setRequestType(HttpRequest::Type::POST);
    std::vector<std::string> headers;
    headers.push_back("Content-Type: application/json; charset=utf-8");
    request->setHeaders(headers);
    request->setResponseCallback(this, callBack);
    
    // write the post data
    const char* postData = postContent.c_str();
    request->setRequestData(postData, strlen(postData));
    
    request->setTag("POST test2");
    HttpClient::getInstance()->send(request);
    request->release();
}
#pragma mark 界面相关的功能函数
// 设置输入框是否可见
void StartLayer::setVisibleUserName(bool isshow)
{
    usernameInputLayer->setVisible(isshow);
}
// qq 微信按钮是否可见
void StartLayer::setVisibleUserQQ(bool isshow)
{
    qqInputLayer->setVisible(isshow);
}
// qq微信登录成功后调用
void StartLayer::setVisibleQQLandOK()
{
    this->loginAutoNode->setVisible(true);
    setVisibleUserQQ(true);
    setVisibleUserName(false);
}

void StartLayer::editBoxEditingDidBegin(EditBox* editBox){}
void StartLayer::editBoxEditingDidEnd(EditBox* editBox){}
void StartLayer::editBoxTextChanged(EditBox* editBox, const std::string& text){}
void StartLayer::editBoxReturn(EditBox* editBox)
{
    if(editBox == editBoxUserName){
        username = editBoxUserName->getText();
    }else if(editBox == editBoxPassWord){
        password = editBoxPassWord->getText();
    }
}

void StartLayer::refreshSList(){
    CCLOG("vectorServerlist.size() = %lu",vectorServerlist.size());
    bool isHave=false;
    for (int i = 0; i<vectorServerlist.size(); i++) {
        std::string lsid = vectorServerlist[i].sid;
        if(this->sid.compare(lsid) == 0){
            isHave = true;
            break;
        }
    }
    /*
    for (int i = 0; i < this->vectorPageList.size(); i ++) {
        for (int j = 0; j < this->vectorPageList[i].serverlist.size(); j++) {
            std::string lsid = this->vectorPageList[i].serverlist[j].sid;
            if(this->sid.compare(lsid) == 0){
                isHave = true;
                break;
            }
        }
    }
     */
    if(!isHave){this->sid = "" ;}
    serverListType list;
    if(vectorServerlist.size() > 0){
        if(this->sid.compare("")==0){
            // 没有默认登录服务器
            list = vectorServerlist[vectorServerlist.size()-1];
            this->sid = list.sid;
        }else{
            // 有默认登录服务器
            /*
            for (int i = 0; i < this->vectorPageList.size(); i ++) {
                for (int j = 0; j < this->vectorPageList[i].serverlist.size(); j++) {
                    std::string lsid = this->vectorPageList[i].serverlist[j].sid;
                    if(this->sid.compare(lsid) == 0){
                        list = this->vectorPageList[i].serverlist[j];
                        break;
                    }
                }
            }

            */
            
            for (int i = 0; i<vectorServerlist.size(); i++) {
                std::string lsid = vectorServerlist[i].sid;
                if(this->sid.compare(lsid) == 0){
                    list = vectorServerlist[i];
                    break;
                }
            }
            
        }
        CCLOG("显示 服务器名称:%s sid:%s",list.sname.c_str(),this->sid.c_str());
        //        Size visibleSize = Director::getInstance()->getVisibleSize();
        
        StartData::getInstance()->serverName = list.sname.c_str();
        
        stXin->setVisible(list.status==4);
        stLiu->setVisible(list.status==3);
        stRe->setVisible(list.status==2);
        stTui->setVisible(list.status==1);
        stWei->setVisible(list.status==0);
        stYu->setVisible(list.status==5);
        
        //1推2热3流4新5预0维
        Color3B color;
        if(list.status == 1) {
            color = SERVER_NAME_COLOR_ST1;
        }else if(list.status == 2) {
            color = SERVER_NAME_COLOR_ST2;
        }else if(list.status == 3) {
            color = SERVER_NAME_COLOR_ST3;
        }else if(list.status == 4) {
            color = SERVER_NAME_COLOR_ST4;
        }else if(list.status == 5) {
            color = SERVER_NAME_COLOR_ST5;
        }else if(list.status == 0) {
            color = SERVER_NAME_COLOR_ST0;
        }else{
            color = SERVER_NAME_COLOR_ST0;
        }
        this->severalNameLabel->setColor(color);
        
        this->severalNameLabel->setString(list.sname);
        //        username = UserDefault::getInstance()->getStringForKey(UKEY_USER);
        //        password = UserDefault::getInstance()->getStringForKey(UKEY_PASS);
        CCLOG("usernmae = %s  password = %s",username.c_str(),password.c_str());
        if(username.compare("") != 0 && password.compare("") != 0){
            editBoxUserName->setText(username.c_str());
            editBoxPassWord->setText(password.c_str());
            this->showUserNameLabel->setString(username.c_str());
            this->loginAutoNode->setVisible(true);
            this->loginNode->setVisible(false);
            this->registeredNode->setVisible(false);
            
            if(PINGTAN=="70" or PINGTAN=="71") {
                this->showUserNameLabel->setString("Đổi tài khoản");
            }
            
        }
        /*
         Menu *mc = (Menu *)this->getChildByTag(5202);
         if(mc){
         MenuItemFont *mif=(MenuItemFont *)mc->getChildByTag(5201);
         mif->setString(list.name);
         }else{
         auto mslist = MenuItemFont::create(list.name, CC_CALLBACK_1(StartLayer::onShowServerList, this));
         mslist->setPosition(Point(visibleSize.width/2,visibleSize.height/6));
         mslist->setTag(5201);
         auto menu = Menu::create(mslist, NULL);
         menu->setPosition(Point::ZERO);
         menu->setTag(5202);
         this->addChild(menu);
         }
         */
    }else{
        //labelInfo->setString("无服务器列表");
        DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604375));
    }
}

void StartLayer::onShowServerList(cocos2d::Ref *pSender)
{
    if(this->vectorPageList.size() == 0)
    {
        return;
    }

    this->logoSP->setVisible(false);
    
    ServerListLayer * slistLayer = ServerListLayer::create();
    slistLayer->setTag(5200);
    slistLayer->setDelegate(this);
    slistLayer->setSList(vectorPageList);
    slistLayer->setRecently(this->recentlyServerID);
    this->addChild(slistLayer);
}

void StartLayer::passServerList(){
    this->logoSP->setVisible(true);
    this->loginAutoNode->setVisible(true);
}

void StartLayer::selectServerList(serverListType sl){
    
    this->loginAutoNode->setVisible(true);
    
    CCLOG("name:%s  sid=%s",sl.sname.c_str(),sl.sid.c_str());
    vectorServerlist.push_back(sl);
    this->sid = sl.sid;
    StartData::getInstance()->serverName = sl.sname.c_str();
    this->refreshSList();
    
    //    ServerListLayer *slistLayer = (ServerListLayer *)this->getChildByTag(5200);
    //    this->removeChild(slistLayer);
    
}

void StartLayer::menuRegistered(cocos2d::Ref *pSender){
    if(username.compare("")==0 || password.compare("")==0){
        //labelInfo->setString("用户名或密码不能为空");
        DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604377));
        return;
    }
    
    
    // 登录
    //    std::string sdf = "{\"userId\": \"qazwsxedc\",\"password\":\"11111\"}";
    //    std::string url = IP_URL + LOGIN_URL;
    //    sendHTTP(url, sdf, callfuncND_selector(StartLayer::onLogin));
    
    
	//type;         fid, 用户授权后得到的accessToken
	//fid;          设备唯一ID，根据MAC地址判断
	//openUUID:     密码：DES
	//psw:          nick name
	//nickName;     user id
	//userId;       user name
	//userName;     platform name
	//platformName; 微博地址
    //profileUrl
    //    std::string sdf = "{\"type\":\"5\",\"fid\":\"2e916fa0-884b-4f08-a43b-20600d018184\",\"openUUID\":\"0\",\"psw\":\"0\",\"nickName\":\"qazwsxedc\",\"userId\":\"qazwsxedc\",\"userName\":\"qazwsxedc\",\"platformName\":\"10001\",\"profileUrl\":\"0\"}";
    //    std::string url = IP_URL + "login.do";
    //    sendHTTP(url, sdf, callfuncND_selector(StartLayer::onLoginWeb));
    
    // 获取服务器列表
    //    std::string sdf = "{\"openUUID\": \"qazwsxedc\",\"platform\":\"11111\",\"platformName\":\"10001\"}";
    //    std::string url = IP_URL + SERVICELIST_URL;
    //    sendHTTP(url, sdf, callfuncND_selector(StartLayer::onLoginRequestServerlist));
    
    // 登录到游戏服务器
    //    std::string sdf = "{\"uid\":\"lq.qazwsxedc\",\"sid\":\"100\"}";
    //    std::string url = IP_URL + "updatepassport.do";
    //    sendHTTP(url, sdf, callfuncND_selector(StartLayer::onGameLogin));
}

void StartLayer::menuLoginCallback(Ref* pSender)
{
}


//void StartLayer::sendAnnouncement()
//{    
//    rapidjson::Document fromScratch;
//    fromScratch.SetObject();
//    rapidjson::Document::AllocatorType& allocator = fromScratch.GetAllocator();
//    fromScratch.AddMember("gameId", YOUXIHAO, allocator);
//    fromScratch.AddMember("version", VERSION.c_str(), allocator);
//    fromScratch.AddMember("platform", PINGTAN.c_str(), allocator);
//    
//    rapidjson::StringBuffer strbuf;
//	rapidjson::Writer<rapidjson::StringBuffer> writer(strbuf);
//	fromScratch.Accept(writer);
//    printf("请求公告--\n%s\n--\n", strbuf.GetString());
//    
//    
//    // 请求公告
//    std::string url = IP_URL + GETANNOUNCE;
//    sendHTTP(url, strbuf.GetString(), callfuncND_selector(StartLayer::onAnnouncement));
//}

//void StartLayer::onAnnouncement(Node *sender, void *data)
//{
//    HttpResponse *response = (HttpResponse*)data;
//    
//    if(response->getResponseCode() == 200){
//        std::vector<char> *buffer = response->getResponseData();
//        std::string bufs(buffer->begin(),buffer->end());
//        CCLOG("请求公告:%s",bufs.c_str());
//        
//        rapidjson::Document d1;
//        d1.Parse<0>(bufs.c_str());
//        std::string value = d1["announce"].GetString();
//        
//        auto announcement = AnnouncementLayer::createLayer();
//        announcement->initUI(value);
//        this->addChild(announcement);
//
//        
//    }else{
//        CCLOG("请求公告失败(%ld)",response->getResponseCode());
//        String *str = String::createWithFormat("请求公告失败(%ld)",
//                                               response->getResponseCode());
//        //labelInfo->setString(str->getCString());
//        DialogPrompt::create()->OnShow(str->getCString());
//    }
//}

#pragma mark 自有帐号注册结果通知
void StartLayer::onLoginRequestCompleted(Node *sender, void *data)
{
    
    //注册需要发送 userId 和 authorization
    
    // 注册请求回调处理
    HttpResponse *response = (HttpResponse*)data;
    
    if(response->getResponseCode() == 200){
        std::vector<char> *buffer = response->getResponseData();
        std::string bufs(buffer->begin(),buffer->end());
        CCLOG("收到注册数据:%s",bufs.c_str());
        
        rapidjson::Document d1;
        d1.Parse<0>(bufs.c_str());
        int code = d1["code"].GetInt();// ==0失败 ==1成功
        std::string desc = d1["desc"].GetString();
        log("code: %d", code);
        log("desc: %s", desc.c_str());
        // 如果 d1['code'] == 1 了   注册就成功了
        
        if(code == 1){
            this->loginAutoNode->setVisible(true);
            this->loginNode->setVisible(false);
            this->registeredNode->setVisible(false);
            
            //labelInfo->setString("注册成功了");
            
            // 保存下
            CCLOG("注册成功了, u=%s  p=%s",username.c_str(),password.c_str());
            UserDefault::getInstance()->setStringForKey(UKEY_USER, username.c_str());
            UserDefault::getInstance()->setStringForKey(UKEY_PASS, password.c_str());
            UserDefault::getInstance()->flush();
            // 注册成功了就去登录到请求服务器列表
            sendValidation();
            
        }else{
            std::string str = WPlatform::getInstance()->getTipInfo(604376) + desc;
            //labelInfo->setString(str.c_str());
            //DialogPrompt::create()->OnShow("注册失败,该帐号已被注册");
            
            //            std::string key = StringUtils::format("R%s",desc.c_str());
            //            std::string value = HclcData::getInstance()->getLuaVarOneOfTable("tipsInfo", key.c_str());
            //            CCLOG("value = %s",value.c_str());
            
            if(desc.compare("100019") == 0) {
                DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604377));
            }else if(desc.compare("100020") == 0) {
                DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604378));
            }else if(desc.compare("100021") == 0) {
                DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604408));
            }
            
        }
        
    }else{
        CCLOG("注册连接请求失败(%ld)",response->getResponseCode());
        String *str = String::createWithFormat(WPlatform::getInstance()->getTipInfo(604379).c_str(),
                                               response->getResponseCode());
        //labelInfo->setString(str->getCString());
        DialogPrompt::create()->OnShow(str->getCString());
    }
    DialogWaiting::getInstance()->OnClose();
}

#pragma mark 第一次登录，自有登录校验，第三方SDK登录不走这个方法
void StartLayer::sendValidation()
{
    rapidjson::Document fromScratch;
    fromScratch.SetObject();
    rapidjson::Document::AllocatorType& allocator = fromScratch.GetAllocator();
    fromScratch.AddMember("userId", rapidjson::StringRef(username.c_str()), allocator);
    fromScratch.AddMember("password", rapidjson::StringRef(password.c_str()), allocator);
    
    rapidjson::StringBuffer strbuf;
	rapidjson::Writer<rapidjson::StringBuffer> writer(strbuf);
	fromScratch.Accept(writer);
    printf("登录--\n%s\n--\n", strbuf.GetString());
    
    
    // 登录
    std::string url = IP_URL + LOGIN_URL;
    sendHTTP(url, strbuf.GetString(), callfuncND_selector(StartLayer::onLogin));
    pushCode = "";
}

#pragma mark 自有第一次登录结果
void StartLayer::onLogin(Node *sender, void *data){
    //登录需要发送 userId 和 password
    pushCode = "";
    // 登录请求回调
    HttpResponse *response = (HttpResponse*)data;
    if(response->getResponseCode() == 200){
        std::vector<char> *buffer = response->getResponseData();
        std::string bufs(buffer->begin(),buffer->end());
        CCLOG("收到登录数据:%s",bufs.c_str());
        
        rapidjson::Document d1;
        d1.Parse<0>(bufs.c_str());
        
        std::string nickname = d1["nickName"].GetString();
        int result = d1["result"].GetInt(); // ==0失败 ==1成功
        std::string sessionID = d1["sessionId"].GetString();
        std::string userID = d1["userId"].GetString();
        
        log("nickname=%s",nickname.c_str());
        log("result=%d",result);
        log("sessionID=%s",sessionID.c_str());
        log("userID=%s",userID.c_str());
        
        if(result == 1){
            //labelInfo->setString("登录(1)成功了");
            StartData::getInstance()->sessionID = sessionID;
            sendLogindo(PINGTAN.c_str(), sessionID, UserDefault::getInstance()->getStringForKey("LQOPENUUID").c_str(), nickname, userID,userID, UserDefault::getInstance()->getStringForKey("LQPUSHTOKEN").c_str(),UserDefault::getInstance()->getStringForKey("LQOSVERSION").c_str(),UserDefault::getInstance()->getStringForKey("LQOSRESOLUTION").c_str(),UserDefault::getInstance()->getStringForKey("LQEXT1").c_str(),UserDefault::getInstance()->getStringForKey("LQEXT2").c_str(),UserDefault::getInstance()->getStringForKey("LQEXT3").c_str(),UserDefault::getInstance()->getStringForKey("LQEXT4").c_str(),UserDefault::getInstance()->getStringForKey("LQEXT5").c_str());
        }else{
            //labelInfo->setString("登录(1)失败了");
            DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604380));
            DialogWaiting::getInstance()->OnClose();
        }
        
    }else{
        String *str = String::createWithFormat(WPlatform::getInstance()->getTipInfo(604381).c_str(),response->getResponseCode());
        //labelInfo->setString(str->getCString());
        DialogPrompt::create()->OnShow(str->getCString());
        
        DialogWaiting::getInstance()->OnClose();
    }
    
}


#pragma mark 第二次登录，从这儿开始分支和主干就走统一流程了
void StartLayer::sendLogindo(std::string type,std::string fid,std::string openUUID,std::string nickName,std::string userID,std::string userName,std::string pushToken,std::string osversion,std::string osresolution,std::string ext1,std::string ext2,std::string ext3,std::string ext4,std::string ext5)
{
    vectorPageList.clear();
    // //**
    rapidjson::Document fromScratch;
    fromScratch.SetObject();
    rapidjson::Document::AllocatorType& allocator = fromScratch.GetAllocator();
    fromScratch.AddMember("type", rapidjson::StringRef(type.c_str()), allocator); // 平台相关
    fromScratch.AddMember("fid", rapidjson::StringRef(fid.c_str()), allocator);
    fromScratch.AddMember("openUUID", rapidjson::StringRef(openUUID.c_str()), allocator);
    fromScratch.AddMember("nickName", rapidjson::StringRef(nickName.c_str()), allocator);
    fromScratch.AddMember("userId", rapidjson::StringRef(userID.c_str()), allocator);
    fromScratch.AddMember("userName", rapidjson::StringRef(userName.c_str()), allocator);
    fromScratch.AddMember("gameId", YOUXIHAO, allocator);
    fromScratch.AddMember("pushToken", rapidjson::StringRef(pushToken.c_str()), allocator);
    
    fromScratch.AddMember("userType", USERTYPE, allocator);// 内部0,这个始终传0，如果有特殊需要修改的，直接通知服务端改数据库
    fromScratch.AddMember("version", rapidjson::StringRef(LQVERSION.c_str()), allocator);// build code 数字的
    currentVersion = UD->getStringForKey(RESDOWNVERKEYASSETS);
    if(currentVersion.compare("") == 0){
        currentVersion = UP_DATE_VER;
    }
    fromScratch.AddMember("resVersion", rapidjson::StringRef(currentVersion.c_str()), allocator);//资源更新版本数字的
    
    fromScratch.AddMember("osversion", rapidjson::StringRef(osversion.c_str()), allocator);
    fromScratch.AddMember("osresolution", rapidjson::StringRef(osresolution.c_str()), allocator);
    fromScratch.AddMember("ext1", rapidjson::StringRef(ext1.c_str()), allocator);
    fromScratch.AddMember("ext2", rapidjson::StringRef(ext2.c_str()), allocator);
    fromScratch.AddMember("ext3", rapidjson::StringRef(ext3.c_str()), allocator);
    fromScratch.AddMember("ext4", rapidjson::StringRef(ext4.c_str()), allocator);
    fromScratch.AddMember("ext5", rapidjson::StringRef(ext5.c_str()), allocator);
    
    getPageServerDate.type = type;
    getPageServerDate.fid = fid;
    getPageServerDate.userId = userID;
    getPageServerDate.gameId = YOUXIHAO;
    getPageServerDate.userType = USERTYPE;
    getPageServerDate.version = LQVERSION;
    
    rapidjson::StringBuffer strbuf;
    rapidjson::Writer<rapidjson::StringBuffer> writer(strbuf);
    fromScratch.Accept(writer);
    printf("登录(2)--\n%s\n--\n", strbuf.GetString());
    
    
    //type;         fid, 用户授权后得到的accessToken
    //fid;          设备唯一ID，根据MAC地址判断
    //openUUID:     密码：DES
    //psw:          nick name
    //nickName;     user id
    //userId;       user name
    //userName;     platform name
    //platformName; 微博地址
    //profileUrl
    //std::string sdf = "{\"type\":\"5\",\"fid\":\"2e916fa0-884b-4f08-a43b-20600d018184\",\"openUUID\":\"0\",\"psw\":\"0\",\"nickName\":\"qazwsxedc\",\"userId\":\"qazwsxedc\",\"userName\":\"qazwsxedc\",\"platformName\":\"10001\",\"profileUrl\":\"0\"}";
    std::string url = IP_URL + LOGIN2_URL.c_str();
    sendHTTP(url, strbuf.GetString(), callfuncND_selector(StartLayer::onLoginWeb));
    DialogWaiting::getInstance()->OnShow(WPlatform::getInstance()->getTipInfo(604366));
    pushCode = "";
}


#pragma mark 第二次登录结果回调
void StartLayer::onLoginWeb(Node *sender, void *data)
{
//    DialogWaiting::getInstance()->OnClose();
    //    /** 服务器列表 */
    //    private ServerListData[] slist;
    //    /** 状态：0，登录失败。1，成功。2，需要更新客户端。*/
    //    private int status;
    
    //    /** 强制更新的app更新地址 */ 状态2接受
    //    private String appUrl;
    
    
    //    /** 资源更新地址 */  状态1接受
    //    private String url;
    //    /** 上一次登录的服务器，或者默认服务器 */
    //    private String sid;
    //    /** userID */
    //    private String userID;
    //    /** nickName */
    //    private String name;
    //    /** 平台的ID */
    //    private int type;
    //    /** 更新或者维护公告,没有为空 */
    int isShowDownUI = 1;
    vectorServerlist.clear();
    
    HttpResponse *response = (HttpResponse*)data;
    if(response->getResponseCode() == 200){
        std::vector<char> *buffer = response->getResponseData();
        std::string bufs(buffer->begin(),buffer->end());
        CCLOG("收到登录数据:%s",bufs.c_str());
        
        rapidjson::Document d1;
        d1.Parse<0>(bufs.c_str());
        int status = d1["status"].GetInt();
        CCLOG("status = %d",status);
        if (status == 0) { // 登录失败
            CCLOG("登录失败了");
            
            //dialogUDLayer->setVisible(false);
            //registeredNode->setVisible(false);
            //loginNode->setVisible(false);
            //loginAutoNode->setVisible(false);
            
            DialogWaiting::getInstance()->OnClose();
            DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604382));
            return;
        } else if (status == 1 || status == 3) {
            //            std::string urlar = d1["url"].GetString();//更新资源的url 不为""的就更新资源
            //            int ver = d1["ver"].GetInt(); // 更新资源的最新版本
            std::string urlar = d1["fixed"].GetString();
            std::vector<MSG_ASSETSLIST> msgAssetsList; // 热更可见的队列
            const rapidjson::Value &pArrayDwon = d1["url"];
            for (rapidjson::SizeType i = 0; i < pArrayDwon.Size(); i++){
                const rapidjson::Value &valueEnt = pArrayDwon[i];
                const rapidjson::Value &jsid = valueEnt["c"];
                std::string ver = StringUtils::format("%d",jsid.GetInt()); // 版本
                const rapidjson::Value &jsname = valueEnt["u"];
                std::string urlpath = jsname.GetString();
                const rapidjson::Value &jstatus = valueEnt["s"];
                float size = jstatus.GetDouble();
                const rapidjson::Value &jstatut = valueEnt["t"];
                float state = jstatut.GetInt(); // 0:可见 1：不可见
                if(state == 0){
                    isShowDownUI = 0;
                }
                MSG_ASSETSLIST slist = {urlpath.c_str(),urlar.c_str(),ver.c_str(),size};
                msgAssetsList.push_back(slist);
            }
            pushCode = d1["pushCode"].GetString();
            std::string svesid = d1["sid"].GetString();
            std::string userID = d1["userID"].GetString();
            StartData::getInstance()->uid = userID;
            std::string name = d1["name"].GetString();
            int type = d1["type"].GetInt();
            
            StartData::getInstance()->ptyps = type;
            
            std::string clientUrl = d1["clientUrl"].GetString();
            StatisticalManage::getInstance()->statisticalURL = clientUrl;
            
            std::string announce = d1["announce"].GetString(); // 公告
            
            // 获取默认服务器列表
            this->recentlyServerID = svesid;
            int starpos = svesid.find("_");
            if(starpos != -1) {
                this->sid = svesid.substr(0,starpos);
            }else{
                this->sid = svesid;
            }
            
            if(announce.compare("") != 0) {
                auto announcement = AnnouncementLayer::createLayer();
                announcement->initUI(announce);
                this->addChild(announcement);
            }
            
            //if(url.compare("") != 0){
            if(ISOPENDOWNLOAD) {
                if(pArrayDwon.Size() > 0){
                    UpdateAssetsLayer * alayer = UpdateAssetsLayer::create();
                    alayer->setDelegate(this);
                    if(isShowDownUI == 0) {
                        alayer->setDownLoadMsgUrl(msgAssetsList);       // 提示热更版本
                    }else{
                        alayer->setDownLoadAutoMsgUrl(msgAssetsList); // 不提示直接热更版本
                    }
                    this->addChild(alayer);
                }
            }
            CCLOG("更新验证完了");
            if(status == 3) {
                CCLOG("status == 33333 l");
                getActivateCodeURL = d1["getCodeUrl"].GetString();
                showActivate();
                CCLOG("status == 33333 完了");
            }
            CCLOG("整个都玩了");
        } else if (status == 2) { // 需要更新客户端
            std::string appUrl = d1["appUrl"].GetString();
            CCLOG("需要更新客户端了  弹出来个界面  然后直接跳转到URL上");
            updateClientURL = appUrl;
            
            dialogUDLayer->setVisible(true);
            
            registeredNode->setVisible(false);
            loginNode->setVisible(false);
            loginAutoNode->setVisible(false);
            DialogWaiting::getInstance()->OnClose();
            return;
        }
        const rapidjson::Value &pArrayServerPageDatas = d1["serverPageDatas"];
        for (rapidjson::SizeType i = 0; i < pArrayServerPageDatas.Size(); i++) {
            const rapidjson::Value &valueEnt = pArrayServerPageDatas[i];
            const rapidjson::Value &jpageId = valueEnt["pId"];
            int pageId = jpageId.GetInt();
            const rapidjson::Value &jpageName = valueEnt["pn"];
            std::string pageName = jpageName.GetString();
            sServerPage page;
            page.pageId = pageId;
            page.pageName = pageName;
            vectorPageList.push_back(page);
        }
        
        /*
         struct serverGetSendDataType{
         int type;//服务器获得type
         std::string fid;//accessTocken
         std::string userId;
         int gameId;
         int userType;
         int version;
         int page;
         };
         */
        const rapidjson::Value &pArray = d1["slist"];
        for (rapidjson::SizeType i = 0; i < pArray.Size(); i++){
            const rapidjson::Value &valueEnt = pArray[i];
            
            const rapidjson::Value &jsid = valueEnt["sid"];
            std::string sid = StringUtils::format("%d",jsid.GetInt());
            const rapidjson::Value &jsname = valueEnt["sname"];
            std::string sname = jsname.GetString();
            const rapidjson::Value &jstatus = valueEnt["status"];
            int status = jstatus.GetInt();
            const rapidjson::Value &jdesc = valueEnt["desc"];
            std::string sdesc = "";
            if(!jdesc.IsNull()){
                sdesc = jdesc.GetString();
            }
            const rapidjson::Value &JserverIP = valueEnt["serverIP"];
            std::string serverIP = JserverIP.GetString();
            
            
            const rapidjson::Value &Juid = valueEnt["uid"];
            std::string uid = Juid.GetString();
            const rapidjson::Value &Jver = valueEnt["ver"];
            int ver = Jver.GetInt();
            
            CCLOG("解析完的服务器列表 %s:%d:%s:%s",sname.c_str(),status,sid.c_str(),sdesc.c_str());
            /*
             struct serverListType{
             int status;
             std::string sname;
             std::string sid;
             std::string desc;
             std::string serverIP;
             std::string serverPort;
             std::string uid;
             int ver;
             };
             */
            //获取已经登录过的服务器列表
            serverListType slist;// = {status = status,sname = sname,sid = sid,sdesc = sdesc,serverIP = serverIP,uid = uid,ver};
            slist.status = status;
            slist.sname = sname;
            slist.sid = sid;
            slist.desc = sdesc;
            slist.serverIP = serverIP;
            slist.uid = uid;
            slist.ver = ver;
            //vectorServerlist.erase(vectorServerlist.begin());
            vectorServerlist.push_back(slist);
            
        }
        //第一列就是登录过的列表
        vectorPageList.at(0).serverlist = vectorServerlist;
        //labelInfo->setString("获取服务器列表成功");
        
        this->refreshSList();
        
        // 显示版本
        string tem = IP_URL.substr(7,3);
        string t1 = "";
        string strvar = "VER:"+t1+LQVERSION.c_str()+"."+currentVersion.c_str()+"."+tem;
        //verLabel->setString(strvar.c_str());
        
    }else{
        String *str = String::createWithFormat(WPlatform::getInstance()->getTipInfo(604383).c_str(),response->getResponseCode());
        //labelInfo->setString(str->getCString());
        DialogPrompt::create()->OnShow(str->getCString());
    }
    
    DialogWaiting::getInstance()->OnClose();
    
    if(true) return ;
    
    //他们说这个是真正的登录需要发送
    //type;         fid, 用户授权后得到的accessToken
    //fid;          设备唯一ID，根据MAC地址判断
    //openUUID:     密码：DES
    //psw:          nick name
    //nickName;     user id
    //userId;       user name
    //userName;     platform name
    //platformName; 微博地址
    //profileUrl
    /**
     HttpResponse *response = (HttpResponse*)data;
     if(response->getResponseCode() == 200){
     std::vector<char> *buffer = response->getResponseData();
     std::string bufs(buffer->begin(),buffer->end());
     CCLOG("收到登录数据:%s",bufs.c_str());
     
     rapidjson::Document d1;
     d1.Parse<0>(bufs.c_str());
     
     std::string nickName = d1["nickName"].GetString();
     std::string serverIP = d1["serverIP"].GetString();
     int serverPort = d1["serverPort"].GetInt();
     std::string sid = d1["sid"].GetString();
     double time = d1["time"].GetDouble();
     std::string uid = d1["uid"].GetString();
     
     log("nickName=%s",nickName.c_str());
     log("serverIP=%s",serverIP.c_str());
     log("serverPort=%d",serverPort);
     log("sid=%s",sid.c_str());
     log("time=%f",time);
     log("uid=%s",uid.c_str());
     
     StartData::getInstance()->uid = uid;
     this->recentlyServerID = sid;
     this->sid = sid.substr(0,3);
     log("截取下上次默认登录的服务器sid 后面的登录过要的时候在家 this->sid = %s",this->sid.c_str());
     sendServerList();
     
     }else{
     String *str = String::createWithFormat("登录连接请求失败(%ld)",response->getResponseCode());
     //labelInfo->setString(str->getCString());
     DialogPrompt::create()->OnShow(str->getCString());
     
     DialogWaiting::getInstance()->OnClose();
     }
     */
}
//代理  获得登录数据json串
serverGetSendDataType StartLayer::getServerPage(int page)
{
    getPageServerDate.page = page;
    return getPageServerDate;
}




#pragma mark 请求服务器列表
void StartLayer::sendServerList()
{
    StartData::getInstance()->openUUID = "";
    
    rapidjson::Document fromScratch;
    fromScratch.SetObject();
    rapidjson::Document::AllocatorType& allocator = fromScratch.GetAllocator();
    fromScratch.AddMember("openUUID", rapidjson::StringRef(StartData::getInstance()->openUUID.c_str()), allocator);//机器码啥的
    fromScratch.AddMember("platform", rapidjson::StringRef(PINGTAN.c_str()), allocator); // 平台编号  上面那个5那个
    fromScratch.AddMember("gameId", YOUXIHAO, allocator); // 游戏标示  上面那个10001那个
    fromScratch.AddMember("version", rapidjson::StringRef(LQVERSION.c_str()), allocator); // 版本
    
    
    rapidjson::StringBuffer strbuf;
	rapidjson::Writer<rapidjson::StringBuffer> writer(strbuf);
	fromScratch.Accept(writer);
    printf("获取服务器列表--\n%s\n--\n", strbuf.GetString());  
    
    //    std::string sdf = "{\"openUUID\": \"qazwsxedc\",\"platform\":\"平台标号 5\",\"platformName\":\"10001\"}";
    std::string url = IP_URL + SERVICELIST_URL;
    sendHTTP(url, strbuf.GetString(), callfuncND_selector(StartLayer::onLoginRequestServerlist));
    DialogWaiting::getInstance()->OnShow(WPlatform::getInstance()->getTipInfo(604366));
}

#pragma mark 获取服务器列表回调
void StartLayer::onLoginRequestServerlist(Node *sender, void *data)
{
    vectorServerlist.clear();
    //注册需要发送 userId 和 authorization
    
    // 请求服务器列表回调
    HttpResponse *response = (HttpResponse*)data;
    if(response->getResponseCode() == 200){
        std::vector<char> *buffer = response->getResponseData();
        std::string bufs(buffer->begin(),buffer->end());
        CCLOG("收到服务器列表数据:%s",bufs.c_str());
        
        rapidjson::Document d;
        d.Parse<0>(bufs.c_str());
        if (d.HasParseError()){  //解析错误
            CCLOG("GetParseError %s\n",d.GetParseError());
        }else {
            //log("json1: %s", d["hello"].GetString());
            std::string dataVersion = d["dataVersion"].GetString();
            CCLOG("dataVersion=%s",dataVersion.c_str());
            
            const rapidjson::Value &a=d["gameServerDatas"];  //读取数组
            if (a.IsArray()){  //判断是不是数组
                for(unsigned int i=0;i<a.Size();++i){ //如果不是数组，这一行会报错
                    const rapidjson::Value &val = a[i]; //得到单个对象
                    //if(val.HasMember("ID")){ //判断这个对象有没有id键
                    const rapidjson::Value &val_id = val["isEnabled"];
                    int  isEnabled = val_id.GetInt();
                    CCLOG(" --> isEnabled=%d",isEnabled);
                    
                    const rapidjson::Value &val_String = val["serverName"];
                    std::string serverName = val_String.GetString();
                    CCLOG(" --> serverName = %s",serverName.c_str());
                    
                    const rapidjson::Value &val_Sid = val["sid"];
                    std::string sid = val_Sid.GetString();
                    CCLOG(" --> sid = %s",sid.c_str());
                    
                    serverListType slist = {isEnabled,serverName,sid};
                    //vectorServerlist.erase(vectorServerlist.begin());
                    vectorServerlist.push_back(slist);
                    
                    // 测试
                    /*
                     for (int j=0; j<4; j++) {
                     String *st = String::createWithFormat("测试%s-%d",serverName.c_str(),j);
                     String *ssi = String::createWithFormat("%s%d",sid.c_str(),j);
                     
                     CCLOG("j=%d, 服务器名称:%s, sid=%s",j,st->getCString(),ssi->getCString());
                     
                     sServerlist slist = {isEnabled+j,st->getCString(),ssi->getCString()};
                     vectorServerlist.push_back(slist);
                     }*/
                    //
                }
            }
        }
        
        //labelInfo->setString("获取服务器列表成功");
        this->refreshSList();
        
    }else{
        String *str = String::createWithFormat(WPlatform::getInstance()->getTipInfo(604384).c_str(),response->getResponseCode());
        //labelInfo->setString(str->getCString());
        DialogPrompt::create()->OnShow(str->getCString());
    }
    DialogWaiting::getInstance()->OnClose();
}



#pragma mark 登录游戏服务器请求
void StartLayer::sendReSeverIP()
{
    rapidjson::Document fromScratch;
    fromScratch.SetObject();
    rapidjson::Document::AllocatorType& allocator = fromScratch.GetAllocator();
    fromScratch.AddMember("uid", rapidjson::StringRef(StartData::getInstance()->uid.c_str()), allocator); // uid
    fromScratch.AddMember("sid", rapidjson::StringRef(this->sid.c_str()), allocator); // 服务器列表的sid
    
    rapidjson::StringBuffer strbuf;
	rapidjson::Writer<rapidjson::StringBuffer> writer(strbuf);
	fromScratch.Accept(writer);
    printf("获取服务器IP地址--\n%s\n--\n", strbuf.GetString());
    
    //std::string sdf = "{\"uid\":\"lq.qazwsxedc\",\"sid\":\"100\"}";
    std::string url = IP_URL + "updatepassport.do";
    sendHTTP(url, strbuf.GetString(), callfuncND_selector(StartLayer::onGameLogin));
    DialogWaiting::getInstance()->OnShow(WPlatform::getInstance()->getTipInfo(604366));
}

#pragma mark 登录游戏服务器回调
void StartLayer::onGameLogin(Node *sender, void *data)
{
    //登录到游戏服务器 uid 和 sid
    
    // 登录请求回调
    HttpResponse *response = (HttpResponse*)data;
    if(response->getResponseCode() == 200){
        std::vector<char> *buffer = response->getResponseData();
        std::string bufs(buffer->begin(),buffer->end());
        CCLOG("收到登录到游戏服务器数据:%s",bufs.c_str());
        
        rapidjson::Document d1;
        d1.Parse<0>(bufs.c_str());
        
        std::string serverIP = d1["serverIP"].GetString();
        int serverPort = d1["serverPort"].GetInt();
        std::string sid = d1["sid"].GetString();
        double time = d1["time"].GetDouble();
        std::string uid = d1["uid"].GetString();
        int ver = d1["ver"].GetInt();
        
        log("  连接服务器...");
        log("  serverIP=%s",serverIP.c_str());
        log("  serverPort=%d",serverPort);
        log("  sid=%s",sid.c_str());
        log("  time=%f",time);
        log("  uid=%s",uid.c_str());
        log("  ver=%d",ver);
        
        if(serverIP.compare("")!=0){
            StartData::getInstance()->ip = serverIP;
            StartData::getInstance()->port = serverPort;
            StartData::getInstance()->time = time;
            StartData::getInstance()->usertype = USERTYPE;
            StartData::getInstance()->sid = this->sid;
            StartData::getInstance()->ver = StringUtils::format("%d",ver);
            // 保存下
            CCLOG("保存用户名:%s 密码:%s",username.c_str(),password.c_str());
            UserDefault::getInstance()->setStringForKey(UKEY_USER, username.c_str());
            UserDefault::getInstance()->setStringForKey(UKEY_PASS, password.c_str());
            UserDefault::getInstance()->flush();
            
            toLuaGame();
        }
        
    }else{
        String *str = String::createWithFormat(WPlatform::getInstance()->getTipInfo(604385).c_str(),response->getResponseCode());
        //labelInfo->setString(str->getCString());
        DialogPrompt::create()->OnShow(str->getCString());
    }
    DialogWaiting::getInstance()->OnClose();
}

#pragma mark 下面内容和SDK相关，请尽量不要修改
//针对SDK做下修改，加入一个初始化SDK的步骤
void StartLayer::initSdk()
{
    // bds 下面这块是给分支预留的内容
    //  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // 下面的内容接入了SDK的分支和主干是不一致的，修改下面的内容的话请一定和SDK接入人员进行确认
    //  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    ///******************************************************************///
        CCLOG("initSDKCPP");
        NDKHelper::AddSelector("initsdk", "initSdkCallBack", callfuncND_selector(StartLayer::initSdkCallBack),this);
        __Dictionary* param = __Dictionary::create();
        param->setObject(__String::create("hello world"), "cppparam");
        SendMessageWithParams("initSdk", param);
    ///******************************************************************///
}
//针对SDK做的修改，SDK初始化完成之后会调这个方法
void StartLayer::initSdkCallBack(Node *node, void *data)
{
    // bds 下面这块是给分支预留的内容
    //  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // 下面的内容接入了SDK的分支和主干是不一致的，修改下面的内容的话请一定和SDK接入人员进行确认
    //  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    ///******************************************************************///
        __Dictionary *callbackParam;
        if(data != NULL){//成功收到回传
            callbackParam = (__Dictionary *)data;
            __String* param1 = (__String*)callbackParam->objectForKey("param1");    //参数1用来表示走哪个平台，用来初始化的，0是自有登录，1是微信登录,2是第三方登录
            __String* param2 = (__String*)callbackParam->objectForKey("param2");    //参数2用来表示是否走自有登录，0表示第三方登录，1表示自有登录
            __String* param3 = (__String*)callbackParam->objectForKey("param3");
            __String* param4 = (__String*)callbackParam->objectForKey("param4");
            __String* param5 = (__String*)callbackParam->objectForKey("param5");
            
        
            CCLOG("param1:%s",param1->getCString());
            CCLOG("param2:%s",param2->getCString());
            CCLOG("param3:%s",param3->getCString());
            CCLOG("param4:%s",param4->getCString());
            CCLOG("param5:%s",param5->getCString());
        
            //根据sdk返回值，修改当前的登录类型
            if(param1->compare("0") == 0){
                landingTypes = 0;
            }else if(param1->compare("1") == 0){
                CCLOG("采用界面风格1");
                landingTypes = 1;
            }else if(param1->compare("2") == 0){
                landingTypes = 2;
            }
            //根据传入值得来决定是否是自有的登录
            if(param2->compare("0") == 0){
                isSelfLanding = false;
            }else if(param2->compare("1") == 0){
                isSelfLanding = true;
            }
        }
    
        //移除回掉监听
        CCLOG("initSDKComplete");
        NDKHelper::RemoveSelectorsInGroup(const_cast<char*>("initsdk"));

        this->initLayer();
        this->initCCBUI();
    ///******************************************************************///
}
//调用第三方SDK的登录的时候会走到这，走自有登录的话不调这个方法
void StartLayer::login()
{
    // bds 下面这块是给分支预留的内容
    ///******************************************************************///
        NDKHelper::AddSelector("login", "loginCallBack", callfuncND_selector(StartLayer::loginCallBack),this);
        SendMessageWithParams("login", NULL);
    ///******************************************************************///
}
//第三方SDK登录成功之后会调用这个方法，走自有登录的话不调这个方法
void StartLayer::loginCallBack(Node *node, void *data)
{
    // bds 下面这块是给分支预留的内容
    ///******************************************************************///
        CCLOG("logincallbackcpp");
        //移除NDK监听
        NDKHelper::RemoveSelectorsInGroup(const_cast<char*>("login"));
        __Dictionary *callbackParam;
        if(data != NULL){//成功收到回传
            callbackParam = (__Dictionary *)data;
            __String* loginResult = (__String*)callbackParam->objectForKey("loginResult");
            if(loginResult->compare("ok") == 0){//登录成功
                __String* type = (__String*)callbackParam->objectForKey("type");
                __String* fid = (__String*)callbackParam->objectForKey("fid");
                __String* openUUID = (__String*)callbackParam->objectForKey("openUUID");
                __String* nickName = (__String*)callbackParam->objectForKey("nickName");
                __String* userID = (__String*)callbackParam->objectForKey("userID");
                __String* userName = (__String*)callbackParam->objectForKey("userName");
                __String* pushToken = (__String*)callbackParam->objectForKey("pushToken");
                __String* osversion = (__String*)callbackParam->objectForKey("osversion");
                __String* osresolution = (__String*)callbackParam->objectForKey("osresolution");
                __String* ext1 = (__String*)callbackParam->objectForKey("ext1");
                __String* ext2 = (__String*)callbackParam->objectForKey("ext2");
                __String* ext3 = (__String*)callbackParam->objectForKey("ext3");
                __String* ext4 = (__String*)callbackParam->objectForKey("ext4");
                __String* ext5 = (__String*)callbackParam->objectForKey("ext5");

                CCLOG("type:%s",type->getCString());
                CCLOG("fid:%s",fid->getCString());
                CCLOG("openUUID:%s",openUUID->getCString());
                CCLOG("nickName:%s",nickName->getCString());
                CCLOG("userID:%s",userID->getCString());
                CCLOG("userName:%s",userName->getCString());
                CCLOG("pushToken:%s",pushToken->getCString());
                CCLOG("osversion:%s",osversion->getCString());
                CCLOG("osresolution:%s",osresolution->getCString());
                CCLOG("ext1:%s",ext1->getCString());
                CCLOG("ext2:%s",ext2->getCString());
                CCLOG("ext3:%s",ext3->getCString());
                CCLOG("ext4:%s",ext4->getCString());
                CCLOG("ext5:%s",ext5->getCString());
            
                //保存设备信息和平台信息
                // bds 不知道为什么报错，而且这儿也没用了 就注掉了
//                UserDefault::getInstance()->setStringForKey("LQPINGTAIID", type->getCString());
//                UserDefault::getInstance()->setStringForKey("LQOPENUUID", openUUID->getCString());
                // bds 保存下当前平台号
                PINGTAN = type->getCString();
                // 保存下
                username = userName->getCString();
                password = userName->getCString();
                CCLOG("保存用户名:%s",username.c_str());
                UserDefault::getInstance()->setStringForKey(UKEY_USER, username.c_str());
                UserDefault::getInstance()->flush();
                this->showUserNameLabel->setString(username.c_str());
                
                if(PINGTAN=="70" or PINGTAN=="71") {
                    this->showUserNameLabel->setString("Đổi tài khoản");
                }
                    
                //发送登录信息
                sendLogindo(StringUtils::format("%s",type->getCString()),
                            StringUtils::format("%s",fid->getCString()),
                            StringUtils::format("%s",openUUID->getCString()),
                            StringUtils::format("%s",nickName->getCString()),
                            StringUtils::format("%s",userID->getCString()),
                            StringUtils::format("%s",userName->getCString()),
                            StringUtils::format("%s",pushToken->getCString()),
                            StringUtils::format("%s",osversion->getCString()),
                            StringUtils::format("%s",osresolution->getCString()),
                            StringUtils::format("%s",ext1->getCString()),
                            StringUtils::format("%s",ext2->getCString()),
                            StringUtils::format("%s",ext3->getCString()),
                            StringUtils::format("%s",ext4->getCString()),
                            StringUtils::format("%s",ext5->getCString()));
            
            }else{//登录状态不是成功
                __String* info = (__String*)callbackParam->objectForKey("info");
                std::string failReason = StringUtils::format("登录失败，原因%s",info->getCString());
                DialogPrompt::create()->OnShow(failReason);
            }
        }else{//data == NULL 的情况
            DialogPrompt::create()->OnShow("登陆异常，请重试。");
        }

    ///******************************************************************///
}

#pragma mark 这儿就进入游戏了

void StartLayer::toLuaGame()
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    // android 返回后黑屏问题
    // 但是没找到[LUA ERROR] function refid '2' does not reference a Lua function 错误
    // MAKE一下
    ScriptEngineManager::getInstance()->removeScriptEngine();
#endif
    auto engine = LuaEngine::getInstance();
    ScriptEngineManager::getInstance()->setScriptEngine(engine);
    
    lua_State* L = engine->getLuaStack()->getLuaState();
    lua_module_register(L);
    
    LuaStack* stack = engine->getLuaStack();
    stack->setXXTEAKeyAndSign("2dxLua", strlen("2dxLua"), "XXTEA", strlen("XXTEA"));
    
    // 注册自定义调用的类
    luaopen_LQSocketBuffer(engine->getLuaStack()->getLuaState());
    tolua_ComService_open(engine->getLuaStack()->getLuaState());
    luaopen_StartData(engine->getLuaStack()->getLuaState());
    luaopen_LoadBattlefield(engine->getLuaStack()->getLuaState());
    luaopen_LabelChange(engine->getLuaStack()->getLuaState());
    luaopen_CoverView(engine->getLuaStack()->getLuaState());
    tolua_videoview_extension_open(engine->getLuaStack()->getLuaState());
    luaopen_StatisticalManage(engine->getLuaStack()->getLuaState());
    luaopen_GameVoice(engine->getLuaStack()->getLuaState());
    luaopen_WPlatform(engine->getLuaStack()->getLuaState());
    
    Texture2D::setDefaultAlphaPixelFormat(Texture2D::PixelFormat::RGBA8888);
    std::string path = FileUtils::getInstance()->fullPathForFilename("code/cc/entry.lua");
    CCLOG("path= %s",path.c_str());
    engine->executeScriptFile(path.c_str());
    
    // 释放资源
    //    loginNode->release();
    //    registeredNode->release();
    //    loginAutoNode->release();
    //    showUserNameLabel->release();
    //    severalNameLabel->release();
    this->removeFromParentAndCleanup(true);
    
    Director::getInstance()->getTextureCache()->removeAllTextures();//removeUnusedTextures();
    SpriteFrameCache::getInstance()->removeUnusedSpriteFrames();//removeUnusedSpriteFrames();
}

void StartLayer::updateAssetsCallback(){
    Label *label = (Label *)this->getChildByTag(12398498);
    if(label){
        std::string var = UD->getStringForKey(RESDOWNVERKEYASSETS);
        label->setString(var.c_str());
    }
}